# TLC调试功能开发
```基于实际应用中的需求, 为toolbox扩展新功能```

## 需求
- [ ] [代码断点调试](#1.代码断点调试开发)
- [ ] 状态查看
- [ ] 动画与代码交互

## 1. 代码断点调试开发
tlc不支持调试功能，在写规约的过程中给用户带来一定的不便。同时在实际使用tlc进行模型检验时，如果没有error，该工具不会展示运行结果。

### 1.1 预期效果
- 查看当前状态下所有可能动作；
- 自由选择动作并且执行；

### 1.2 实现细节

#### 1.2.1 tlc的实现

tla2tools.jar包实现了模型检验的核心功能，我们可以使用```java tlc2.TLC [-option] inputfile```的方式执行模型检验，接下来详细介绍这个功能是如何实现的。
#### tlc2.TLC
这个类提供了整个jar包的主方法`TLC.main`。在main()函数共调用了两个比较重要的方法:
- tlc.handleParameters(args): 负责分析传递进来的参数, e.g., ```java tlc2.TLC -simulate spec[.tla]```可以执行simulate模式，```1. Simulation of TLA+ specs: java tlc2.TLC -modelcheck spec[.tla]```则是模型检验。
- tlc.process(): 运行TLC

#### tlc2.TLC.process
在执行模型检验之前，该方法通过
```TLCStandardMBean modelCheckerMXWrapper = TLCStandardMBean.getNullTLCStandardMBean();```注册了一个JMXBean工具。这个工具支持用户查看内存，暂停运行等功能。详见[SUSPEND_API](./SUSPEND_API.md)。
接下来通过if-else语句分别执行Simulation模式或者ModelCheck模式，我们先关注ModelCheck模式。

```java
if (isBFS())
{
    TLCGlobals.mainChecker = new ModelChecker(tool, metadir, stateWriter, deadlock, fromChkpt,
            FPSetFactory.getFPSetInitialized(fpSetConfiguration, metadir, mainFile), startTime);
    modelCheckerMXWrapper = new ModelCheckerMXWrapper((ModelChecker) TLCGlobals.mainChecker, this);
    result = TLCGlobals.mainChecker.modelCheck();
} else
{
    TLCGlobals.mainChecker = new DFIDModelChecker(tool, metadir, stateWriter, deadlock, fromChkpt, startTime);
    result = TLCGlobals.mainChecker.modelCheck();
}
```
通过又一个if-else语句来执行广度优先还是深度优先遍历。不同的遍历方式实例化不同的mainChecker对象，`ModelChecker`类和`DFIDModelChecker`类。

#### tlc2.tool.AbstractChecker
```public abstract class AbstractChecker```这个类定义为抽象类，`ModelChecker`和`DFIDModelChecker`都是这个类的子类。定义的重要变量有:
- `IWorker[] workers`: 线程集合
- `ITool tool`: 处理Spec文件的核心类
提供的方法有:
- modelCheck(): model checker的主方法
- modelCheckImpl(): 被modelCheck()调用，定义为抽线方法。不同的子类有不同的实现方式。
- runTLC(int): 负责完成除了分析初始状态外的所有工作，包括启动所有模型检验的线程，输出运行信息，终止线程等等。
- stop、suspend、resume: 与上文提到的JMXBean有关，是这三个接口的具体实现。

#### tlc2.tool.Tool
继承了抽象类`Spec`，该类实现了对spec文件的分析处理，包括变量定义，常量定义，初始状态，次态等等。
提供的几个重要方法:
- getInitStates(): 获取初态
- getNextStates(Action, TLCState): 获取次态
- eval: 表达式分析


#### tlc2.tool.Worker
实现了接口`IWorker`，同时继承了类`IdThread`。改线程类的功能是从状态队列中获取一个状态，然后生成所有可能的新状态，检测Invariant，更新发现的状态集合以及未检测状态队列。

#### tlc2.tool.queue.StateQueue
这是一个抽线类，是底层存储未遍历状态的一个队列。
定义的变量包括:
- len: 队列长度；
- numwaiting: 等待的线程；
- `volatile finish`: **可见**变量，标志是否完成遍历。

提供的方法有:
- enqueue: 入队列，非线程安全；
- dequeue: 出队列，非线程安全；
- sEnqueue: 入队列，线程安全，使用了synchronized进行同步；
- sDequeue：出队列，线程安全，使用了synchronized进行同步。

#### 1.2.2 暂定的实现
定义`DebugChecker`为`ModelChecker`的子类，这样实现有两个好处：
- 复用了ModelChecker的方法，支持从特定的某一步骤后开始模型检验。
- 可维护性高，调试这部分主要由子类负责。

## 2. 当前进度
- [x] 理清源代码中各个类实例的具体方式及相应变量的设置
- [ ] 完成`DebugChecker`类
  - [x] 载入spec文件完成分析处理及其环境配置
  - [ ] 定义所有相关的函数
- [ ] UI设置
- [ ] 可视化 
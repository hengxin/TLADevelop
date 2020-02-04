# TLADevelop

`A project of adding debug function for toolbox.`

## Eclipse实现架构

- [ ] **Eclipse Runtime实现**

- [ ] **command**和**handler**机制

- [ ] **Eclipse RCP**
  - 涉及的核心组件
    - SWT(Standard Widget Toolkit)用户组件交互库
    - JFace: 在SWT的基础上对一些控件进行分装
    - 

- [ ] **Maven**管理和构建项目

--**WindowBuilder神器**--

## SourceCode

- [TOOLBOX](###TOOLBOX)
- [DOC](###DOC)

### TOOLBOX

#### JOB(任务)

- *NewTLAModuleCreate*: 新建tlamodule

#### Spec(规约相关)

- Manager(事件管理)
  - *ISpecManager*: (父类)负责处理TLC打开的spec, 包括获取最近打开的spec、新增、通过路径获取spec
  - *WorkspaceSpecManager*: (具体实现)继承*ISpecManager*
- Nature(性质?)
  - *ParserHelper*: 与编译spec相关
  - *TLANature*: tla+ nature(性质?)
  - *TLAParsingBuilder*: 编译构建
  - *TLAParsingBuilderConstants*:
- Parser(编译)
  - *DoubleHashedTable*: hashtable结构, 在*ParserDepencyStorage*中使用
  - *IParseConstants*: 编译过程中使用的常量定义
  - *IParseResultListener*: 接口, 编译结果时间获取
  - *ModuleParserLauncher*: 启动SANY
  - *ParserDependencyStorage*: module间的依赖关系
  - *ParseResult*: 编译的状态
  - *ParseResultBroadcaster*: 编译的状态返回给监听者
  - *SpecificationParserLauncher*: 编译spec的根文件(root file)
- *Module*: 载入tla module信息
- *Spec*: 与编辑Spec相关, 包括新建、跳转至某个定义、获取上次修改内容等等
  
#### Tool(接口及工具)

  - *IParseResult*: 编译结果
  - *SpecEvent*: spec当前状态
  - *SpecLifecycleParticipant*: 接口
  - *SpecRenameEvent*: 重命名事件
  - *ToolboxHandle*: 提供toolbox一些内部方法的接口

#### UI(界面)

- contribution
  - *ModuleListContributionItem*: module列表
  - *ParseStatusContributionIem*: 编译状态
  - *SizeControlContribution*: 尺寸
  - *SpecListContributionItem*: spec列表
- Dialog
  - *InformationDialog*: 弹出信息框(警告、错误等等)
- Expression
  - *currentSpecTester*: 当前选中的spec
  - *ParseErrorTester*: 是否有编译错误
- Handle(句柄)
  - *AddModuleHandler*: 新建module
  - *CloseSpecHandler*: 关闭module
  - *DeleteModuleHandler*: 删除
  - *ForgetSpecHandler*
  - *HelpHandler*
  - *ModulePropertiesHandler*:
  - *NewSpecHandler*
  - *OpenModuleHandler*
  - *OpenParseErrorViewHandler*
  - *OpenSpecHandler*
  - *OpenSpecHandlerDelegate*
  - *OpenViewHandler*
  - *ParseModuleHandler*
  - *ParseSpecHandler*
  - *PropertiesHandler*
  - *RefreshSpecHandler*
  - *RenameSpecHandler*
  - *SaveDirtyEditorAbstractHandler*
  - *ShowHistoryHandler*
  - *SwitchPerspectiveHandler*
- Navigator
  - *ToolboxExplorer*
  - *ToolboxExplorerResourceListener*
- Perspective
  - *InitialPerspective*
  - *SpecLoaderPerspective*
- Preference(preference界面)
  - *EditorPreferencePage*
  - *GeneralPreferencePage*
  - *LibraruPathComposite*
  - *ParserPreferencePage*
  - *SWTFactory*
  - *TranslatorPreferencePage*
- Property(property界面)
  - *GenericFieldEditorPropertyPage*
  - *GenericSelectionProvider*
  - *ModulePropertyPage*
  - *SpecpropertyPage*
- Provider
  - *IGroup*
  - *SpecContentProvider*:
- View(几个子界面,包括pdf生成、问题、欢迎界面)
  - *PDFBrowser*
  - *PDFBrowserEditor*
  - *ProblemView*
  - *ProblemViewResourceListener*
  - *ToolboxWelcomeView*
- Wizard(向导)
  - *NewSpecWizard*:
  - *NewSpecWizardPage*

#### UTIL(工具包)

  - Compare(比较方法)
    - *MarkerComparator*: IMaker比较
    - *ResourceNameComparator*: 根据命名比较
    - *SpecComparator*: 根据修改时间对spec进行排序
  - E4
    - *E4HandlerWrapper*: E4(?) handle 
  - Pref
    - *IPreferenceConstants*: 一些与preference有关的常量的定义
    - *PreferenceInitializer*: 上述常量的初始化动作
    - *PreferenceStoreHelper*: 为每个文件存储上述常量的设置
    - *ResourceBasedPrefernceStore*: 
    - *UnwantedPreferenceManger*: pref管理需求
  - *AdapterFactory*: 适配器(类型匹配)工厂
  - *ChangedModulesGatheringDeltaVisitor*: module变动展示
  - *FontPreferenceChangeListener*: 字体修改监视器
  - *HelpButton*: helpbutton的实现
  - *IHelpConstatns*: help system的常量定义
  - *LegacyFileDocumentProvider*: 重写refreshFile功能
  - *PopupMessage*: 弹出消息框
  - *RCPNameToFilelStream*: RCP映射TLA module地址
  - ***ResourceHelper***: 定义了许多和文件资源相关方法, 包括增添改删module、project等
  - *SpecLifecycleManager*: spec 生命周期管理, 包括许多participant扩展的管理
  - *StringHelper*: 一些处理字符串的方法
  - *StringSet*: 实现的string集合
  - *TLAMarkerHelper*: TLA的一些标志
  - *TLAMarkerInformationHolder*: 
  - *TLAMarkerResolutionGenerator*: 
  - *TLAtoPCalMarker*: tla to pcal
  - *ToolboxJob*: 
  - ***UIHelper***: 处理RCP对象
- *AbstractTLCActivator*:
- *Activator*:
- *OpenFileManager*: openFileManager 监听器
- *ToolboxDirectoryVistor*:
  
### DOC

- handler(句柄)
  - *HelpContentsHandler*: 
  - *HelpPDFHandler*: 
  - *HelpURLHandler*: 
- *HelpActivator*:
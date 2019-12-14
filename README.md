# TLADevelop
`A project of adding debug function for toolbox.`
## SourceCode
- [TOOLBOX](###TOOLBOX)
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
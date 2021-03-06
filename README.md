# Fail Incomplete Pattern Matches
A simple Maven project that demonstrates how to configure the Scala compiler to fail when incomplete/non-exhaustive 
pattern matches have been detected.

## Objective
Non-exhaustive pattern match warnings (_'warning: match may not be exhaustive.'_) result in the logging of #toString 
for the matched object, which is a PII concern. To remedy this side-effect, the Scala compiler will be configured to 
fail at compile-time at the presence of non-exhaustive pattern matches.

### Part A: Investigate Scala Compiler Options
What compiler flags are available to promote pattern match warnings to errors? There are a variety of flags (referred 
to as options), which include the standard options, advanced options, and private options.

#### Standard Compiler Options
Below is a complete list of the Scala compiler standard options:
```
Usage: scalac <options> <source files>
where possible standard options include:
  -Dproperty=value                Pass -Dproperty=value directly to the runtime system.
  -J<flag>                        Pass <flag> directly to the runtime system.
  -P:<plugin>:<opt>               Pass an option to a plugin
  -X                              Print a synopsis of advanced options.
  -bootclasspath <path>           Override location of bootstrap class files.
  -classpath <path>               Specify where to find user class files.
  -d <directory|jar>              destination for generated classfiles.
  -dependencyfile <file>          Set dependency tracking file.
  -deprecation                    Emit warning and location for usages of deprecated APIs.
  -encoding <encoding>            Specify character encoding used by source files.
  -explaintypes                   Explain type errors in more detail.
  -extdirs <path>                 Override location of installed extensions.
  -feature                        Emit warning and location for usages of features that should be imported explicitly.
  -g:<level>                      Set level of generated debugging info. (none,source,line,vars,notailcalls) default:vars
  -help                           Print a synopsis of standard options
  -javabootclasspath <path>       Override java boot classpath.
  -javaextdirs <path>             Override java extdirs classpath.
  -language:<_,feature,-feature>  Enable or disable language features: `_' for all, `-language:help' to list
  -no-specialization              Ignore @specialize annotations.
  -nobootcp                       Do not use the boot classpath for the scala jars.
  -nowarn                         Generate no warnings.
  -optimise                       Generates faster bytecode by applying optimisations to the program
  -print                          Print program with Scala-specific features removed.
  -sourcepath <path>              Specify location(s) of source files.
  -target:<target>                Target platform for object files. All JVM 1.5 targets are deprecated. (jvm-1.5,jvm-1.6,jvm-1.7,jvm-1.8) default:jvm-1.6
  -toolcp <path>                  Add to the runner classpath.
  -unchecked                      Enable additional warnings where generated code depends on assumptions.
  -uniqid                         Uniquely tag all identifiers in debugging output.
  -usejavacp                      Utilize the java.class.path in classpath resolution.
  -usemanifestcp                  Utilize the manifest in classpath resolution.
  -verbose                        Output messages about what the compiler is doing.
  -version                        Print product version and exit.
  @<file>                         A text file containing compiler arguments (options and source files)
```
#### Advanced Compiler Options
Below is a complete list of the Scala compiler advanced options:
```
Usage: scalac <options> <source files>

-- Notes on option parsing --
Boolean settings are always false unless set.
Where multiple values are accepted, they should be comma-separated.
  example: -Xplugin:option1,option2
<phases> means one or a comma-separated list of:
  (partial) phase names, phase ids, phase id ranges, or the string "all".
  example: -Xprint:all prints all phases.
  example: -Xprint:expl,24-26 prints phases explicitouter, closelim, dce, jvm.
  example: -Xprint:-4 prints only the phases up to typer.

Possible advanced options include:
  -Xcheckinit                    Wrap field accessors to throw an exception on uninitialized access.
  -Xdev                          Indicates user is a developer - issue warnings about anything which seems amiss
  -Xdisable-assertions           Generate no assertions or assumptions.
  -Xelide-below <n>              Calls to @elidable methods are omitted if method priority is lower than argument
  -Xexperimental                 Enable experimental extensions.
  -Xfatal-warnings               Fail the compilation if there are any warnings.
  -Xfull-lubs                    Retains pre 2.10 behavior of less aggressive truncation of least upper bounds.
  -Xfuture                       Turn on future language features.
  -Xgenerate-phase-graph <file>  Generate the phase graphs (outputs .dot files) to fileX.dot.
  -Xlint:<_,warning,-warning>    Enable or disable specific warnings: `_' for all, `-Xlint:help' to list
  -Xlog-free-terms               Print a message when reification creates a free term.
  -Xlog-free-types               Print a message when reification resorts to generating a free type.
  -Xlog-implicit-conversions     Print a message whenever an implicit conversion is inserted.
  -Xlog-implicits                Show more detail on why some implicits are not applicable.
  -Xlog-reflective-calls         Print a message when a reflective method call is generated
  -Xmacro-settings:<option>      Custom settings for macros.
  -Xmain-class <path>            Class for manifest's Main-Class entry (only useful with -d <jar>)
  -Xmax-classfile-name <n>       Maximum filename length for generated classes
  -Xmigration:<version>          Warn about constructs whose behavior may have changed since version.
  -Xno-forwarders                Do not generate static forwarders in mirror classes.
  -Xno-patmat-analysis           Don't perform exhaustivity/unreachability analysis. Also, ignore @switch annotation.
  -Xno-uescape                   Disable handling of \u unicode escapes.
  -Xnojline                      Do not use JLine for editing.
  -Xplugin:<paths>               Load a plugin from each classpath.
  -Xplugin-disable:<plugin>      Disable plugins by name.
  -Xplugin-list                  Print a synopsis of loaded plugins.
  -Xplugin-require:<plugin>      Abort if a named plugin is not loaded.
  -Xpluginsdir <path>            Path to search for plugin archives.
  -Xprint:<phases>               Print out program after <phases>
  -Xprint-icode[:phases]         Log internal icode to *.icode files after <phases> (default: icode)
  -Xprint-pos                    Print tree positions, as offsets.
  -Xprint-types                  Print tree types (debugging option).
  -Xprompt                       Display a prompt after each error (debugging option).
  -Xresident                     Compiler stays resident: read source filenames from standard input.
  -Xscript <object>              Treat the source file as a script and wrap it in a main method.
  -Xshow-class <class>           Show internal representation of class.
  -Xshow-object <object>         Show internal representation of object.
  -Xshow-phases                  Print a synopsis of compiler phases.
  -Xsource:<version>             Treat compiler input as Scala source for the specified version, see SI-8126.
  -Xsource-reader <classname>    Specify a custom method for reading source files.
  -Xstrict-inference             Don't infer known-unsound types
  -Xverify                       Verify generic signatures in generated bytecode (asm backend only.)
  -Xxml:<_,property,-property>   Configure XML parsing: `_' for all, `-Xxml:help' to list
  -Y                             Print a synopsis of private options.
```
#### Private Compiler Options
Lastly, below is a complete list of the Scala compiler private options:
```
Usage: scalac <options> <source files>

-- Notes on option parsing --
Boolean settings are always false unless set.
Where multiple values are accepted, they should be comma-separated.
  example: -Xplugin:option1,option2
<phases> means one or a comma-separated list of:
  (partial) phase names, phase ids, phase id ranges, or the string "all".
  example: -Xprint:all prints all phases.
  example: -Xprint:expl,24-26 prints phases explicitouter, closelim, dce, jvm.
  example: -Xprint:-4 prints only the phases up to typer.

Possible private options include:
  -Ybackend:<choice of bytecode emitter>  Choice of bytecode emitter. (GenASM,GenBCode) default:GenASM
  -Ybreak-cycles                          Attempt to break cycles encountered during typing
  -Ybrowse:<phases>                       Browse the abstract syntax tree after <phases>
  -Ycheck:<phases>                        Check the tree at the end of <phases>
  -YclasspathImpl:<implementation>        Choose classpath scanning method. (recursive,flat) default:recursive
  -Yclosure-elim                          Perform closure elimination.
  -Ycompact-trees                         Use compact tree printer when displaying trees.
  -Ycompletion:<provider>                 Select tab-completion in the REPL. (pc,adhoc,none) default:pc
  -Yconst-opt                             Perform optimization with constant values.
  -Ydead-code                             Perform dead code elimination.
  -Ydebug                                 Increase the quantity of debugging output.
  -Ydelambdafy:<strategy>                 Strategy used for translating lambdas into JVM code. (inline,method) default:inline
  -Ydisable-unreachable-prevention        Disable the prevention of unreachable blocks in code generation.
  -YdisableFlatCpCaching                  Do not cache flat classpath representation of classpath elements from jars across compiler instances.
  -Ydump-classes <dir>                    Dump the generated bytecode to .class files (useful for reflective compilation that utilizes in-memory classloaders).
  -Ygen-asmp <dir>                        Generate a parallel output directory of .asmp files (ie ASM Textifier output).
  -Ygen-javap <dir>                       Generate a parallel output directory of .javap files.
  -Yinfer-argument-types                  Infer types for arguments of overridden methods.
  -Yinline                                Perform inlining when possible.
  -Yinline-handlers                       Perform exception handler inlining when possible.
  -Yinline-warnings                       Emit inlining warnings. (Normally suppressed due to high volume)
  -Ylinearizer:<which>                    Linearizer to use (normal,dfs,rpo,dump) default:rpo
  -Ylog:<phases>                          Log operations during <phases>
  -Ylog-classpath                         Output information about what classpath is being applied.
  -Ymacro-debug-lite                      Trace essential macro-related activities.
  -Ymacro-debug-verbose                   Trace all macro-related activities: compilation, generation of synthetics, classloading, expansion, exceptions.
  -Ymacro-expand:<policy>                 Control expansion of macros, useful for scaladoc and presentation compiler (normal,none,discard) default:normal
  -Yno-adapted-args                       Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
  -Yno-completion                         Disable tab-completion in the REPL.
  -Yno-generic-signatures                 Suppress generation of generic signatures for Java.
  -Yno-imports                            Compile without importing scala.*, java.lang.*, or Predef.
  -Yno-load-impl-class                    Do not load $class.class files.
  -Yno-predef                             Compile without importing Predef.
  -Ynooptimise                            Clears all the flags set by -optimise. Useful for testing optimizations in isolation.
  -Yopt:<_,optimization,-optimization>    Enable optimizations: `_' for all, `-Yopt:help' to list
  -Yopt-inline-heuristics:<strategy>      Set the heuristics for inlining decisions. (at-inline-annotated,everything) default:at-inline-annotated
  -Yopt-warnings:<_,warning,-warning>     Enable optimizer warnings: `_' for all, `-Yopt-warnings:help' to list
  -Yoverride-objects                      Allow member objects to be overridden.
  -Yoverride-vars                         Allow vars to be overridden.
  -Ypartial-unification                   Enable partial unification in type constructor inference
  -Ypatmat-exhaust-depth <n>              off
  -Ypresentation-any-thread               Allow use of the presentation compiler from any thread
  -Ypresentation-delay <n>                Wait number of ms after typing before starting typechecking
  -Ypresentation-log <file>               Log presentation compiler events into file
  -Ypresentation-replay <file>            Replay presentation compiler events from file
  -Ypresentation-strict                   Do not report type errors in sources with syntax errors.
  -Ypresentation-verbose                  Print information about presentation compiler tasks.
  -Yrangepos                              Use range positions for syntax trees.
  -Yrecursion <n>                         Set recursion depth used when locking symbols.
  -Yreify-copypaste                       Dump the reified trees in copypasteable representation.
  -Yrepl-class-based                      Use classes to wrap REPL snippets instead of objects
  -Yrepl-outdir <path>                    Write repl-generated classfiles to given output directory (use "" to generate a temporary dir)
  -Yrepl-sync                             Do not use asynchronous code for repl startup
  -Yresolve-term-conflict:<strategy>      Resolve term conflicts (package,object,error) default:error
  -Yshow:<phases>                         (Requires -Xshow-class or -Xshow-object) Show after <phases>
  -Yshow-member-pos <output style>        Show start and end positions of members
  -Yshow-symkinds                         Print abbreviated symbol kinds next to symbol names.
  -Yshow-symowners                        Print owner identifiers next to symbol names.
  -Yshow-syms                             Print the AST symbol hierarchy after each phase.
  -Yshow-trees                            (Requires -Xprint:) Print detailed ASTs in formatted form.
  -Yshow-trees-compact                    (Requires -Xprint:) Print detailed ASTs in compact form.
  -Yshow-trees-stringified                (Requires -Xprint:) Print stringifications along with detailed ASTs.
  -Yskip:<phases>                         Skip <phases>
  -Yskip-inline-info-attribute            Do not add the ScalaInlineInfo attribute to classfiles generated by -Ybackend:GenASM
  -Ystatistics:<_,phase,-phase>           Print compiler statistics for specific phases: `_' for all, `-Ystatistics:help' to list
  -Ystop-after:<phases>                   Stop after <phases>
  -Ystop-before:<phases>                  Stop before <phases>
  -Ywarn-adapted-args                     Warn if an argument list is modified to match the receiver.
  -Ywarn-dead-code                        Warn when dead code is identified.
  -Ywarn-inaccessible                     Warn about inaccessible types in method signatures.
  -Ywarn-infer-any                        Warn when a type argument is inferred to be `Any`.
  -Ywarn-nullary-override                 Warn when non-nullary `def f()' overrides nullary `def f'.
  -Ywarn-nullary-unit                     Warn when nullary methods return Unit.
  -Ywarn-numeric-widen                    Warn when numerics are widened.
  -Ywarn-unused                           Warn when local and private vals, vars, defs, and types are unused.
  -Ywarn-unused-import                    Warn when imports are unused.
  -Ywarn-value-discard                    Warn when non-Unit expression results are unused.

Additional debug settings:
  -Ydoc-debug                             Trace all scaladoc activity.
  -Yide-debug                             Generate, validate and output trees using the interactive compiler.
  -Yinfer-debug                           Trace type inference and implicit search.
                                            deprecated: Use -Ytyper-debug
  -Yissue-debug                           Print stack traces when a context issues an error.
  -Ypatmat-debug                          Trace pattern matching translation.
  -Ypos-debug                             Trace position validation.
  -Ypresentation-debug                    Enable debugging output for the presentation compiler.
  -Yquasiquote-debug                      Trace quasiquote-related activities.
  -Yreify-debug                           Trace reification.
  -Ytyper-debug                           Trace all type assignments.

Deprecated settings:
  -Yeta-expand-keeps-star                 Eta-expand varargs methods to T* rather than Seq[T].  This is a temporary option to ease transition.
                                            deprecated: This flag is scheduled for removal in 2.12. If you have a case where you need this flag then please report a bug.
  -Yinfer-by-name                         Allow inference of by-name types. This is a temporary option to ease transition. See SI-7899.
                                            deprecated: This flag is scheduled for removal in 2.12. If you have a case where you need this flag then please report a bug.
  -Yinfer-debug                           Trace type inference and implicit search.
                                            deprecated: Use -Ytyper-debug
  -Ymacro-no-expand                       Don't expand macros. Might be useful for scaladoc and presentation compiler, but will crash anything which uses macros and gets past typer.
                                            deprecated: Use -Ymacro-expand:none
```

## Conclusion
There does not appear to be a compiler option that forces a compile-time failure specifically for pattern match 
warnings ('warning: match may not be exhaustive.'). One potential solution, however, is to set the advanced option 
'-Xfatal-warnings' flag which will fail the compilation for a pattern match warning, as well as any other compiler 
warning(s). The caveat, however, is that this option will fail the build for warnings beyond a pattern match warning, 
as well.
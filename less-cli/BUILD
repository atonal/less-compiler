java_library(
    name = "less-cli",
    srcs = glob([
        "src/main/**/*.java",
    ]),
    resources = glob([
        "src/main/resources/**",
    ]),
    deps = [
        "@net_sourceforge_argparse4j_argparse4j//jar",
        "@org_apache_commons_commons_lang3//jar",
        "//less-core",
    ],
)

java_binary(
    name = "lessc",
    visibility = ["//visibility:public"],
    main_class = "com.squarespace.less.cli.LessC",
    runtime_deps = [
        ":less-cli",
    ],
)

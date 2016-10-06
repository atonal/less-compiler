java_library(
    name = "less-cli",
    srcs = glob([
        "src/main/**/*.java",
    ]),
    resources = glob([
        "src/main/resources/**",
    ]),
    visibility = ["//visibility:public"],
    deps = [
        "@net_sourceforge_argparse4j_argparse4j//jar",
        "@org_apache_commons_commons_lang3//jar",
        "//less-core",
    ],
)

java_binary(
    name = "lessc",
    main_class = "com.squarespace.less.cli.LessC",
    runtime_deps = [
        ":less-cli",
    ],
)

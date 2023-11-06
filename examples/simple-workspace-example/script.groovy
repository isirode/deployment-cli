log.info("Starting script")

// Info : access to global variables
log.info("Global variable : {}", global_var)
log.info("Global table variable : {}", _global_table.my_var)

// Info : access to a workspace
log.info("test_workspace_1 var : {}", test_workspace_1.my_var)
log.info("test_workspace_1 table var : {}", test_workspace_1.my_table.table_var)

// Info : you can use this syntax if the workspace contains a "-"
log.info("test-workspace-2 var : {}", this["test-workspace-2"].my_var)
log.info("test-workspace-2 table var : {}", this["test-workspace-2"].my_table.table_var)
package com.kenny.code.generator;


import org.junit.jupiter.api.Test;

import static com.kenny.code.generator.Generator.generatorToFile;

public class CodeGenTest {


    @Test
    public void testManager() {
        generatorToFile(
                "jdbc:mysql://mysql.aimptest.svc.k5.bigtree.zone:3306/aimp_model", "test_user", "tmOxmmc+3jznq2cX",
                "liukangning", "/Users/liukangning/develop/workspace/myboot/aimp-manager/aimp-manager-service/src/main/java",
                "com.bigtreefinance.aimp.manager",
                "aimp_list,aimp_model,aimp_model_log,aimp_model_version,aimp_node_config,aimp_operation_log,aimp_process,aimp_process_node,aimp_sample,",
                //"aimp_model_log",
                //"aimp_global_warn,aimp_report,aimp_report_log,aimp_stat_score,aimp_stat_score,aimp_score_psi,aimp_param_psi",
                //"aimp_score_psi",
                "", true,
                "use_status", "create_time,create_id,create_name", "edit_id,edit_time,edit_name",
                //true, true, true
                true, true, true, false
        );
    }


}

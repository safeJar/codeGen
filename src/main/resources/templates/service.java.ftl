package ${package.Service};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ${package.Entity}.${entity};
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import ${superServiceClassPackage};

/**
 * ${table.comment!} 服务类
 *
 * @author ${author}
 * @date ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    default ${entity} getOnly(LambdaQueryWrapper<${entity}> wrapper){
        wrapper.last("limit 1");
        return this.getOne(wrapper);
    }

    default LambdaQueryWrapper<${entity}> getLambdaQuery() {
        return Wrappers.lambdaQuery(new ${entity}());
    }

    default LambdaUpdateWrapper<${entity}> getLambdaUpdate() {
        return Wrappers.lambdaUpdate(new ${entity}());
    }
}
</#if>

package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
* ${table.comment!} 前端控制器
*
* @author ${author}
* @date ${date}
*/
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>

    @Resource
    private ${table.serviceName} ${table.entityPath}Service;

    @PostMapping
    public Boolean save(@RequestBody ${entity} ${table.entityPath}) {
        return ${table.entityPath}Service.saveOrUpdate(${table.entityPath});
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return ${table.entityPath}Service.removeById(id);
    }

    @GetMapping
    public List<${entity}> findAll() {
        return ${table.entityPath}Service.list();
    }

    @GetMapping("/{id}")
    public ${entity} findOne(@PathVariable Integer id) {
        return ${table.entityPath}Service.getById(id);
    }

    @GetMapping("/page")
    public Page<${entity}> findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        return ${table.entityPath}Service.page(new Page<>(pageNum, pageSize));
    }

    @PostMapping("/delete/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return ${table.entityPath}Service.removeBatchByIds(ids);
    }
}
</#if>

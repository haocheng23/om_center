package ${package.Controller};

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * ${table.comment!} 前端控制器
 *
 * @author ${author}
 * @since ${date}
*/

@Slf4j
@Api(tags = "${table.comment!}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {

    @Autowired
    public ${table.serviceName} ${table.entityPath}Service;

    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public CommonResult save(@RequestBody ${entity} ${table.entityPath}){
        boolean result = ${table.entityPath}Service.save(${table.entityPath});
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "根据id删除")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        boolean result = ${table.entityPath}Service.removeById(id);
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "条件查询")
    @PostMapping("/get")
    public CommonResult list(@RequestBody ${entity} ${table.entityPath}){
        List<${entity}> ${table.entityPath}List = ${table.entityPath}Service.list(new QueryWrapper<>(${table.entityPath}));
        return CommonResult.success(${table.entityPath}List);
    }

<#--    @ApiOperation(value = "列表（分页）")-->
<#--    @GetMapping("/list/{pageNum}/{pageSize}")-->
<#--    public Object list(@PathVariable("pageNum")Long pageNum, @PathVariable("pageSize")Long pageSize){-->
<#--        IPage<${entity}> page = ${table.entityPath}Service.page(-->
<#--        new Page<>(pageNum, pageSize), null);-->
<#--        return CommonResult.(StatusCode.SUCCESS,"查询成功",new PageResult<>(page.getTotal(),page.getRecords()));-->
<#--    }-->

    @ApiOperation(value = "详情")
    @GetMapping("/get/{id}")
    public CommonResult get(@PathVariable("id") String id){
        ${entity} ${table.entityPath} = ${table.entityPath}Service.getById(id);
        return CommonResult.success(${table.entityPath});
    }

    @ApiOperation(value = "根据id修改")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable("id") Long id, @RequestBody ${entity} ${table.entityPath}){
        ${table.entityPath}.setId(id);
        boolean result = ${table.entityPath}Service.updateById(${table.entityPath});
        if (result) {
            return CommonResult.success(result);
        }
        return CommonResult.failed();
    }
</#if>
}
</#if>
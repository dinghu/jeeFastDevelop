package com.fast.develop.sys.dict.web;

import com.fast.develop.framework.security.annotations.SecCreate;
import com.fast.develop.framework.security.annotations.SecEdit;
import com.fast.develop.framework.security.annotations.SecList;
import com.fast.develop.framework.web.support.Pagination;
import com.fast.develop.framework.web.support.QueryParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fast.develop.sys.dict.entity.DictItem;
import com.fast.develop.sys.dict.service.DictService;
import com.fast.develop.framework.json.JsonResult;
import com.fast.develop.framework.security.annotations.SecDelete;
import com.fast.develop.framework.security.annotations.SecPrivilege;
import com.fast.develop.framework.web.SpringControllerSupport;
import com.fast.develop.framework.web.support.QueryParameter;

@Controller
@RequestMapping("/sys/dict")
public class DictController extends SpringControllerSupport {
    @Autowired
    private DictService dictService;

    @SecPrivilege(title = "字典管理")
    @RequestMapping(value = "/index")
    public String index(Model model) {
        String page = this.getViewPath("index");
        return page;
    }

    @SecList
    @RequestMapping(value = "/list")
    public String list(Model model,
                       @QueryParameter QueryParameters query,
                       @ModelAttribute("page") Pagination page
    ) {
        page = dictService.findPage(query, page);
        return getViewPath("list");
    }

    @SecCreate
    @RequestMapping("/input")
    public String input(@ModelAttribute("dict") DictItem dict, Model model) {

        return getViewPath("input");
    }

    /*
     * 修改用户
     */
    @SecEdit
    @RequestMapping("/input/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        DictItem dict = dictService.getById(id);
        model.addAttribute("dict", dict);
        return getViewPath("input");
    }

    /**
     * @return
     */
    @SecCreate
    @SecEdit
    @RequestMapping("/save")
    public @ResponseBody
    JsonResult save(@ModelAttribute DictItem dict) {

        if (dict.isNew()) {
            dictService.save(dict);
        } else {
            dictService.update(dict);
        }
        return JsonResult.success("保存成功");
    }

    @SecDelete
    @RequestMapping("/delete/{id}")
    public @ResponseBody
    JsonResult delete(@PathVariable("id") Long id) {
        dictService.delete(id);
        return JsonResult.success("成功删除1条");
    }

    @SecDelete
    @RequestMapping("/delete")
    public @ResponseBody
    JsonResult delete(@RequestParam("ids") Long[] ids) {
        if (ids == null || ids.length == 0) {
            return JsonResult.error("没有删除");
        }
        for (Long id : ids) {
            dictService.delete(id);
        }
        return JsonResult.success("成功删除" + ids.length + "条");
    }
}

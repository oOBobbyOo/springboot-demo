package com.company.mybatis.controller;

import com.company.mybatis.entity.Clazz;
import com.company.mybatis.service.ClazzService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 * @author Henry
 */
@RestController
@RequestMapping("/api/classes")
public class ClazzController {

  private final ClazzService clazzService;

  public ClazzController(ClazzService clazzService) {
    this.clazzService = clazzService;
  }

  @PostMapping
  public Clazz create(@RequestBody Clazz clazz) {
    return clazzService.createClass(clazz);
  }

  @PutMapping
  public Clazz update(@PathVariable Integer id, @RequestBody Clazz clazz) {
    return clazzService.updateClass(id, clazz);
  }

  @DeleteMapping
  public String delete(@PathVariable Integer id) {
    clazzService.deleteClass(id);
    return "删除成功";
  }

  @GetMapping("/{id}")
  public Clazz getById(@PathVariable("id") Integer id) {
    return clazzService.getClassById(id);
  }

  @GetMapping
  public List<Clazz> listAll() {
    return clazzService.getAllClasses();
  }
}

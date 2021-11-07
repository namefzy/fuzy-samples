package com.fuzy.example.leetcode.editor.cn;
//给定一个保存员工信息的数据结构，它包含了员工 唯一的 id ，重要度 和 直系下属的 id 。
//
// 比如，员工 1 是员工 2 的领导，员工 2 是员工 3 的领导。他们相应的重要度为 15 , 10 , 5 。那么员工 1 的数据结构是 [1, 15,
// [2]] ，员工 2的 数据结构是 [2, 10, [3]] ，员工 3 的数据结构是 [3, 5, []] 。注意虽然员工 3 也是员工 1 的一个下属，但
//是由于 并不是直系 下属，因此没有体现在员工 1 的数据结构中。 
//
// 现在输入一个公司的所有员工信息，以及单个员工 id ，返回这个员工和他所有下属的重要度之和。 
//
// 
//
// 示例： 
//
// 
//输入：[[1, 5, [2, 3]], [2, 3, []], [3, 3, []]], 1
//输出：11
//解释：
//员工 1 自身的重要度是 5 ，他有两个直系下属 2 和 3 ，而且 2 和 3 的重要度均为 3 。因此员工 1 的总重要度是 5 + 3 + 3 = 1
//1 。
// 
//
// 
//
// 提示： 
//
// 
// 一个员工最多有一个 直系 领导，但是可以有多个 直系 下属 
// 员工数量不超过 2000 。 
// 
// Related Topics 深度优先搜索 广度优先搜索 哈希表 
// 👍 158 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/*
// Definition for Employee.
class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
};
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution690 {
    static int sum = 0;

    public static void main(String[] args) {
        Employee employee1 = new Employee(1,50, Arrays.asList(2));
        Employee employee2 = new Employee(2,89, Arrays.asList(3));
        Employee employee3 = new Employee(3,69, Arrays.asList(4));
        Employee employee4 = new Employee(4,52, Arrays.asList(5,26));
        Employee employee5 = new Employee(5,78, Arrays.asList(6,7));
        Employee employee6 = new Employee(6,63, null);
        Employee employee7 = new Employee(7,55, Arrays.asList(8));
        Employee employee8 = new Employee(8,88, Arrays.asList(9,25));
        Employee employee9 = new Employee(9,64, Arrays.asList(10));
        Employee employee10 = new Employee(10,54, Arrays.asList(11,19));
        Employee employee11 = new Employee(11,81, Arrays.asList(12));
        Employee employee12 = new Employee(12,83, Arrays.asList(13,18));
        Employee employee13 = new Employee(13,58, Arrays.asList(14,15));
        Employee employee14 = new Employee(14,64, null);
        Employee employee15 = new Employee(15,97, Arrays.asList(16));
        Employee employee16 = new Employee(16,67, Arrays.asList(17));
        Employee employee17 = new Employee(17,56, null);
        Employee employee18 = new Employee(18,64, null);
        Employee employee19 = new Employee(19,95, Arrays.asList(20,23));
        Employee employee20 = new Employee(20,70, Arrays.asList(21,22));
        Employee employee21 = new Employee(21,74, null);
        Employee employee22 = new Employee(22,95, null);
        Employee employee23 = new Employee(23,56, Arrays.asList(24));
        Employee employee24 = new Employee(24,100, null);
        Employee employee25 = new Employee(25,69, null);
        Employee employee26 = new Employee(26,98, null);
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        list.add(employee3);
        list.add(employee4);
        list.add(employee5);
        list.add(employee6);
        list.add(employee7);
        list.add(employee8);
        list.add(employee9);
        list.add(employee10);
        list.add(employee11);
        list.add(employee12);
        list.add(employee13);
        list.add(employee14);
        list.add(employee15);
        list.add(employee16);
        list.add(employee17);
        list.add(employee18);
        list.add(employee19);
        list.add(employee20);
        list.add(employee21);
        list.add(employee22);
        list.add(employee23);
        list.add(employee24);
        list.add(employee25);
        list.add(employee26);

        getImportance(list,9);

    }
    public static int getImportance(List<Employee> employees,int id){
        for (Employee employee : employees) {
            if(employee.id==id){
                sum +=employee.importance;
                for (Integer subordinate : employee.subordinates) {
                    getImportance1(employees,subordinate);
                }
            }
        }
        return sum;
    }

    public static int getImportance1(List<Employee> employees, int id) {
        for (Employee employee : employees) {
            if(id==employee.id){
                sum = employee.importance+dfs(employees,employee.subordinates);
            }
        }
        System.out.println(sum);
        return sum;
    }

    public static int dfs(List<Employee> employees,List<Integer> subordinates){
        if(subordinates==null||subordinates.size()==0){
            return 0;
        }
        //ans记录当前子节点的和
        int ans = 0;
        for (Integer subordinate : subordinates) {
            for (Employee employee : employees) {
                if(employee.id==subordinate){
                    //问题：当一个主节点有多个节点时，如果sum=sum+dfs()会导致重复添加sum
                    ans += employee.importance+dfs(employees,employee.subordinates);
                    System.out.println(String.format("id=%s， 重要值=%s，ans=%s",subordinate,employee.importance,ans));
                }
            }
        }
        return sum+ans;
    }

    static class Employee {
        public Employee(int id, int importance, List<Integer> subordinates) {
            this.id = id;
            this.importance = importance;
            this.subordinates = subordinates;
        }

        public int id;
        public int importance;
        public List<Integer> subordinates;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

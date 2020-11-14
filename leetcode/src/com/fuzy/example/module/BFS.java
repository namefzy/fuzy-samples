package com.fuzy.example.module;


import java.util.*;

/**
 * @ClassName BFS
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/14 23:18
 * @Version 1.0.0
 */
public class BFS {

    int BFS(NodeList start, NodeList target){
        //核心数据结构
        Queue<NodeList> q = new LinkedList<>();
        //避免走回头路
        Set<NodeList> visited = new HashSet<>();
        //将起点加入队列
        q.offer(start);
        //记录步数
        int step = 0;

        while (!q.isEmpty()){
            int size = q.size();
            for (int i = 0; i < size; i++) {
                NodeList cur = q.poll();
                //到达终点
                if(cur == target){
                    return step;
                }
                //已下步骤是将cur的相邻节点加入队列
                NodeList left = cur.left;
                NodeList right = cur.right;
                List<NodeList> nodeLists = new ArrayList<>();
                nodeLists.add(left);
                nodeLists.add(right);
                for (NodeList x:nodeLists){
                    if(!visited.contains(x)){
                        q.offer(x);
                        visited.add(x);
                    }
                }
            }
            step++;
        }
        return step;
    }

    public class NodeList{
        private int val;
        private NodeList left;
        private NodeList right;

    }
}

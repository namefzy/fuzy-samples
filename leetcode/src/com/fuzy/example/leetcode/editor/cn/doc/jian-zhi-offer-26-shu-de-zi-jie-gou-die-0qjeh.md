
# [剑指 Offer 26. 树的子结构](https://leetcode-cn.com/problems/shu-de-zi-jie-gou-lcof/)
***
### 思路一：迭代/BFS
我上来想到的就是迭代解法，虽然代码相比递归长了些，但我觉得比递归好理解一些；迭代AC了之后搞了半天才搞出来递归的解法（哭了）`（不过Java用迭代解法是真慢啊，要5ms；Python还行）`
- **总体思路**
   1. 先遍历树A，如果遍历到和B节点值相同的节点，进入helper方法判断接下来的节点是否都相同
   2. 节点都相同返回True；不相同返回False，并且继续遍历树A找下一个相同的节点
   3. 如果遍历完了A还没有返回过True，说明B不是A的子结构，返回False
- **helper方法**：用于判断从A的子树是否有和B相同的部分
   1. 正常BFS步骤，用队列存储树A和B相对应的节点nodeA, nodeB
   2. 因为入队的条件是只要树B节点存在就入队，如果A已经没有了相应节点返回False：if not A
   3. 如果A和B对应节点值不相同也返回False：if nodeA.val != nodeB.val
   4. 如果遍历完了B也没有返回过False，说明B是A的子结构，返回True
### 代码
```Python3 []
class Solution:
    def isSubStructure(self, A: TreeNode, B: TreeNode) -> bool:
        def helper(A, B):
            queue = [(A, B)]
            while queue:
                nodeA, nodeB = queue.pop(0)
                if not nodeA or nodeA.val != nodeB.val:
                    return False
                if nodeB.left:
                    queue.append((nodeA.left, nodeB.left))
                if nodeB.right:
                    queue.append((nodeA.right, nodeB.right))
            return True

        if not B: return False
        queue = collections.deque([A])
        while queue:
            node = queue.popleft()
            if node.val == B.val:
                if helper(node, B):
                    return True
            if node.left:
                queue.append(node.left)
            if node.right:
                queue.append(node.right)
        return False
```

```Java []
class Solution {
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if(B == null) return false;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(A);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            if(node.val == B.val){
                if(helper(node, B)){
                    return true;
                }
            }
            if(node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
        }
        return false;
    }

    private boolean helper(TreeNode nodeA, TreeNode nodeB){
        Queue<TreeNode> queueA = new LinkedList<>();
        Queue<TreeNode> queueB = new LinkedList<>();
        queueA.offer(nodeA);
        queueB.offer(nodeB);

        while(!queueB.isEmpty()){
            nodeA = queueA.poll();
            nodeB = queueB.poll();
            if(nodeA == null || nodeA.val != nodeB.val){
                return false;
            }
            if(nodeB.left != null){
                queueA.offer(nodeA.left);
                queueB.offer(nodeB.left);
            }
            if(nodeB.right != null){
                queueA.offer(nodeA.right);
                queueB.offer(nodeB.right);
            }
        }
        return true;
    }

}
```

**复杂度分析**
> m为A的节点个数，n为B的节点个数
- 时间复杂度：*O(m * n)*，遍历A：O(m)，比较A和B：O(n)
- 空间复杂度：*O(m)*，最差的情况就是遍历了A的所有节点
***
### 思路二：递归/DFS
总体思路和迭代差不多，也是先在树A中找到和B节点值相同的节点，再通过helper方法比较接下来的节点是否和B相同；只不过把BFS换成了DFS。`这种方法Java只要0ms了，Python3的话和迭代差不多，都是100ms左右`
- **dfs方法**：遍历树A的节点nodeA，寻找和B根节点值相同的节点
   1. 特判：如果nodeA不存在，说明已经遍历完树A，遍历完也没找到和B相同的节点，返回False
   2. 如果nodeA.val == B.val，说明已经找到一个相同节点，进入helper方法判断接下来的节点是否相同；相同返回True，不相同则继续遍历树A，找下一个相同节点
   3. 继续遍历nodeA的左右节点：dfs(nodeA.left)和dfs(nodeA.right)，只要两者有一个为True就返回True 
   => `return dfs(nodeA.left) or dfs(nodeA.right)`
这行代码可能不太好理解，可以看以下代码，两者等价
```py
if nodeA.left:
    if dfs(nodeA.left):
        return True
if nodeA.right:
    if dfs(nodeA.right):
        return True
return False
```
- **helper方法**：用于判断从A的子树是否有和B相同的部分
   1. 如果遍历完了B也没有返回过False，说明B确实是A的子结构，返回True：`if not nodeB: return True`
   2. 如果B还没有遍历完，但A先遍历完了，返回False：`if not nodeA: return False`
   3. 如果A和B对应节点值不相同，妥妥地返回False：`if nodeA.val != nodeB.val: return False`
   4. 当前这一对节点(nodeA, nodeB)比较完后，开始比较接下来的对应节点，分别是左子树节点对(nodeA.left, nodeB.left)和右子树节点对(nodeA.right, nodeB.right)；只有左子树和右子树也相同才能返回True，所以最后`return self.helper(nodeA.left, nodeB.left) and self.helper(nodeA.right, nodeB.right)`
### 代码
```Python3 []
class Solution:
    def isSubStructure(self, A: TreeNode, B: TreeNode) -> bool:
        if not B: return False
        def dfs(nodeA):
            if not nodeA: return False
            if nodeA.val == B.val:
                if self.helper(nodeA, B):
                    return True
            return dfs(nodeA.left) or dfs(nodeA.right)
        return dfs(A)
    
    def helper(self, nodeA, nodeB):
        if not nodeB: 
            return True
        if not nodeA or nodeA.val != nodeB.val:
            return False
        return self.helper(nodeA.left, nodeB.left) and self.helper(nodeA.right, nodeB.right)
```

```Java []
class Solution {
    private TreeNode B;
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if(B == null) return false;
        this.B = B;
        return dfs(A);
    }

    private boolean dfs(TreeNode nodeA){
        if(nodeA == null)
            return false;
        if(nodeA.val == B.val)
            if(helper(nodeA, B))
                return true;
        return dfs(nodeA.left) || dfs(nodeA.right);
    }

    private boolean helper(TreeNode nodeA, TreeNode nodeB){
        if(nodeB == null)
            return true;
        if(nodeA == null || nodeA.val != nodeB.val)
            return false;
        return helper(nodeA.left, nodeB.left) && helper(nodeA.right, nodeB.right);
    }
}
```

**复杂度分析**
- 时间复杂度：*O(m * n)*
- 空间复杂度：*O(m)*
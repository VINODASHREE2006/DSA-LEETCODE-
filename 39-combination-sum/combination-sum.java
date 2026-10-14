import java.util.*;
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans=new ArrayList<>();
        solve(candidates, target, 0, new ArrayList<>(), ans);
        return ans;
    }
    public void solve(int[] candidates, int target, int i,
                      List<Integer> list, List<List<Integer>> ans) {
        if (target==0) {
            ans.add(new ArrayList<>(list));
            return;
        }
        if (i==candidates.length||target<0)
            return;
        list.add(candidates[i]);
        solve(candidates, target-candidates[i], i, list, ans);
        list.remove(list.size()-1);
        solve(candidates, target, i+1, list, ans);
    }
}
class Solution {
    int[][][] dp;
    public boolean isScramble(String s1, String s2) {
        int n=s1.length();
        dp = new int[n][n][n+1];
        return solve(s1, s2, 0, 0, n);
    }
    public boolean solve(String s1, String s2, int i, int j, int len) {
        if (s1.substring(i, i+len).equals(s2.substring(j, j+len)))
            return true;
        if (len==1)
            return false;
        if (dp[i][j][len]!=0)
            return dp[i][j][len]==1;
        for (int k=1; k<len; k++) {
            if (solve(s1, s2, i, j, k) &&
                solve(s1, s2, i + k, j + k, len-k)) {
                dp[i][j][len]=1;
                return true;
            }
            if (solve(s1, s2, i, j + len-k, k) &&
                solve(s1, s2, i+k, j, len-k)) {
                dp[i][j][len]=1;
                return true;
            }
        }
        dp[i][j][len]=-1;
        return false;
    }
}
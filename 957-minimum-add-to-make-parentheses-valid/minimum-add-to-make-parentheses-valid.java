class Solution {
    public int minAddToMakeValid(String s) {
        int balance=0;
        int ans=0;
        for(int i=0;i<s.length();i++) {
            if(s.charAt(i)=='(') {
                balance++;
            }
            else {
                balance--;
                if (balance<0) {
                    ans++;
                    balance=0;
                }
            }
        }
        return ans + balance;
    }
}
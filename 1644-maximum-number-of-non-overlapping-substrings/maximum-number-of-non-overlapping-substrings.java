class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();
        int[] first = new int[26];
        int[] last = new int[26];
        Arrays.fill(first, n);
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            first[c] = Math.min(first[c], i);
            last[c] = i;
        }
        List<int[]> list = new ArrayList<>();
        for (int c = 0; c < 26; c++) {
            if (first[c] == n)
                continue;
            int l = first[c];
            int r = last[c];
            boolean valid = true;
            for (int i = l; i <= r; i++) {
                int x = s.charAt(i) - 'a';
                if (first[x] < l) {
                    valid = false;
                    break;
                }
                r = Math.max(r, last[x]);
            }
            if (valid)
                list.add(new int[]{l, r});
        }
        list.sort((a, b) -> a[1] - b[1]);
        List<String> ans = new ArrayList<>();
        int end = -1;
        for (int[] p : list) {
            if (p[0] > end) {
                ans.add(s.substring(p[0], p[1] + 1));
                end = p[1];
            }
        }
        return ans;
    }
}
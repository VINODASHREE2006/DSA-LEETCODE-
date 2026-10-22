class Solution {
    public List<Integer> fallingSquares(int[][] positions) {
        int n = positions.length;
        int[] height = new int[n];
        List<Integer> ans = new ArrayList<>();
        int max = 0;

        for (int i = 0; i < n; i++) {
            int left = positions[i][0];
            int side = positions[i][1];
            int h = side;

            for (int j = 0; j < i; j++) {
                int prevLeft = positions[j][0];
                int prevRight = prevLeft + positions[j][1];
                int currRight = left + side;

                if (left < prevRight && currRight > prevLeft) {
                    h = Math.max(h, height[j] + side);
                }
            }

            height[i] = h;
            max = Math.max(max, h);
            ans.add(max);
        }

        return ans;
    }
}
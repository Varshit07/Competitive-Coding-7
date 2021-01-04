/**
 * Time Complexity: O(NLogN) where N is the length of input array
 * Space Complexity: O(N) where N is the length of input array
 * LeetCode: Y (https://leetcode.com/problems/meeting-rooms-ii/)
 * Approach: 
    Sort the meeting time intervals based on the start time of the meeting. This helps in knowing which meeting do we need to arrange a conference room for.
    Assign a new conference room for the earliest meeting.
    For subsequent meetings, arrange a new conference room if there is no existing room vacant at the time of the start of the meeting. If an existing room is free by the start time of the meeting, then reuse exisiting allocated room.
    So, while scheduling each meeting we need to know the earliest time an already allocated conference room will be vacant. Use a minHeap for this where each node is the ending time of the meeting. So the conference room which will be free the earliest is at the root. 
    So now we know the earliest time at which an allocated conference room will be available. If that time is equal to or after the new meeting needs to be started, then we need to allocate a new conference room for the existing meeting and cannot use any existing conference room.
    If we can reuse that meeting room, then pop the node from the heap.
 */

class Solution {
    public int minMeetingRooms(int[][] intervals) {
        // Check for edge case
        if(intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // Sort meetings based on the start time
        Arrays.sort(intervals, (a,b) -> a[0] - b[0]);
        
        // Initialize a minHeap
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(intervals.length, (a,b) -> a - b);

        // Add the ending time of the first meeting to the heap i.e. regardless of the number of meetings, we need to have at least 1 conference room
        minHeap.add(intervals[0][1]);

        // See the subsequent meetings
        for(int i = 1; i < intervals.length ; i++) {
          // Get the earliest time at which existing meeting room will be free
          int meetingRoomFreeEarliest = minHeap.peek();
            
          // If the new meeting starts at or starts after the earliest time at which existing meeting will then end remove that node i.e. use an existing conference room for the meeting
          if(intervals[i][0] >= meetingRoomFreeEarliest) {
            minHeap.poll();
          }
        
         // add the end time of new meeting to the minHeap regardless of whether we used an already allocated conference room or not
          minHeap.add(intervals[i][1]);
        }

       // after arranging all the meetings, the size of the minHeap denotes how many minimum distinct conference rooms were used
       return minHeap.size();
    }
}

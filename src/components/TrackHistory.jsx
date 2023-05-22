import React, { useState, useEffect } from 'react';

function TrackHistory() {
  const [recentTracks, setRecentTracks] = useState([]);

  useEffect(() => {
    fetchRecentTracks();
  }, []);

  const fetchRecentTracks = async () => {
    try {
      const response = await fetch('http://localhost:8080/track-history');
      const data = await response.json();
      setRecentTracks(data);
    } catch (error) {
      console.error('Error fetching track history:', error);
    }
  };

  const prop = "mt-4 ml-4 mr-4 text-sm text-[#cdd6f4] py-1 px-3 rounded-lg"

  return (
<div className="flex flex-col items-center justify-center min-h-screen bg-[#1e1e2e]">
  <h1 className="text-[#cdd6f4] font-semibold">Here are your last listened 50 songs:</h1>
  <div className="mt-4 flex flex-col items-center mb-8">
    {/* display the recent tracks if available */}
    {recentTracks.length > 0 ? (
      recentTracks.map((track) => (
        <div key={track.name} className="mb-2 text-[#cdd6f4]">
          <span className="text-[#b4befe] font-semibold">{track.artist}</span> - {track.name}
        </div>
      ))
    ) : (
      <div className="text-[#cdd6f4]">No track history found.</div>
    )}
  </div>
</div>

  );
}

export default TrackHistory;

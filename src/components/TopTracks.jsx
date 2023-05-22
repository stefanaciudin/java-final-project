import React, { useState, useEffect } from 'react';

function TopTracks() {
  const [timeRange, setTimeRange] = useState('short_term');
  const [topTracks, setTopTracks] = useState([]);

  const handleTimeRangeChange = (range) => {
    setTimeRange(range);
  };

  useEffect(() => {
    fetchTopTracks();
  }, [timeRange]);

  const fetchTopTracks = async () => {
    try {
      const response = await fetch(`http://localhost:8080/top-tracks?timeRange=${timeRange}`);
      const data = await response.json();
      setTopTracks(data);
    } catch (error) {
      console.error('Error fetching top tracks:', error);
    }
  };

  const prop = "mt-4 ml-4 mr-4 text-sm text-[#cdd6f4] py-1 px-3 rounded-lg";

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-[#1e1e2e]">
      <div className="mb-8">
        {/* Time range buttons */}
        <button
          onClick={() => handleTimeRangeChange('short_term')}
          className={`${prop} ${timeRange === 'short_term' ? 'bg-[#6c7086] hover:bg-[#7f849c]' : 'bg-transparent hover:bg-[#6c7086]'}`}
        >
          Short Term
        </button>
        <button
          onClick={() => handleTimeRangeChange('medium_term')}
          className={`${prop} ${timeRange === 'medium_term' ? 'bg-[#6c7086] hover:bg-[#7f849c]' : 'bg-transparent hover:bg-[#6c7086]'}`}
        >
          Medium Term
        </button>
        <button
          onClick={() => handleTimeRangeChange('long_term')}
          className={`${prop} ${timeRange === 'long_term' ? 'bg-[#6c7086] hover:bg-[#7f849c]' : 'bg-transparent hover:bg-[#6c7086]'}`}
        >
          Long Term
        </button>
      </div>

      <div className="flex flex-col items-center">
        {/* display the top tracks */}
        {topTracks.map((track) => (
          <div key={track.id} className="flex items-center mb-8 text-[#cdd6f4]">
            <img src={track.imageUrl} alt={track.name} className="w-10 h-10 mr-4 object-cover rounded" />
            <div>
              <span className="text-[#b4befe] font-semibold">{track.artists[0].name}</span> - {track.name}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default TopTracks;

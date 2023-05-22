import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

function TopArtists() {
  const [timeRange, setTimeRange] = useState('short_term');
  const [topArtists, setTopArtists] = useState([]);

  const handleTimeRangeChange = (range) => {
    setTimeRange(range);
  };

  useEffect(() => {
    fetchTopArtists();
  }, [timeRange]);

  const fetchTopArtists = async () => {
    try {
      const response = await fetch(`http://localhost:8080/top-artists?timeRange=${timeRange}`);
      const data = await response.json();
      setTopArtists(data);
    } catch (error) {
      console.error('Error fetching top Artists:', error);
    }
  };

  const prop = "mt-4 ml-4 mr-4 text-sm text-[#cdd6f4] py-1 px-3 rounded-lg"

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
        {/* display the top artists */}
        {topArtists.map((artist) => (
          <div key={artist.name} className="flex flex-col items-center mb-8 text-[#cdd6f4]">
            <img src={artist.imageUrl} alt={artist.name} className="w-40 h-40 object-cover rounded-full" />
            <p className="mt-2 text-[#b4befe] font-semibold">{artist.name}</p>
            <p>{artist.genres.join(', ')}</p>
          </div>
        ))}
      </div>

      {/* link to FollowedArtists page */}
      <div className="my-8 text-[#cdd6f4] text-center font-semibold">
        <p>Curious about your followed artists?</p>
        <p>Click <Link to="/stats/followed-artists" className="underline text-[#b4befe]">here</Link> to view your followed artists.</p>
      </div>
    </div>
  );
}

export default TopArtists;

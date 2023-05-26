import React, { useState, useEffect } from 'react';

function TopGenres() {
  const [timeRange, setTimeRange] = useState('short_term');
  const [topGenres, setTopGenres] = useState([]);

  const handleTimeRangeChange = (range) => {
    setTimeRange(range);
  };

  useEffect(() => {
    fetchTopGenres();
  }, [timeRange]);

  const fetchTopGenres = async () => {
    try {
      const response = await fetch(`http://localhost:8080/top-genres?timeRange=${timeRange}`);
      const data = await response.json();
      setTopGenres(data);
    } catch (error) {
      console.error('Error fetching top genres:', error);
    }
  };

  return (
    <div className="flex-col items-center justify-center min-h-screen bg-[#1e1e2e]">
      
      <div className="flex justify-center mb-8">
        <button
          onClick={() => handleTimeRangeChange('short_term')}
          className={`mt-2 ml-4 mr-4 text-m text-[#cdd6f4] py-1 px-3 rounded-lg ${
            timeRange === 'short_term' ? 'bg-[#6c7086] hover:bg-[#7f849c]' : 'bg-transparent hover:bg-[#6c7086]'
          }`}
        >
          Short Term
        </button>
        <button
          onClick={() => handleTimeRangeChange('medium_term')}
          className={`mt-2 ml-4 mr-4 text-m text-[#cdd6f4] py-1 px-3 rounded-lg ${
            timeRange === 'medium_term' ? 'bg-[#6c7086] hover:bg-[#7f849c]' : 'bg-transparent hover:bg-[#6c7086]'
          }`}
        >
          Medium Term
        </button>
        <button
          onClick={() => handleTimeRangeChange('long_term')}
          className={`mt-2 ml-4 mr-4 text-m text-[#cdd6f4] py-1 px-3 rounded-lg ${
            timeRange === 'long_term' ? 'bg-[#6c7086] hover:bg-[#7f849c]' : 'bg-transparent hover:bg-[#6c7086]'
          }`}
        >
          Long Term
        </button>
      </div>

      <div className="flex flex-col items-center">
        <p className="text-lg font-semibold mb-4 text-[#cdd6f4] text-xl">Your top genres based on listening history:</p>
        
        {/* display the top genres */}
        {topGenres.map((genre, index) => {
          const genreName = Object.keys(genre)[0];
          const genreCount = genre[genreName];
          const totalGenresCount = topGenres.slice(0, 25).reduce((sum, g) => sum + g[Object.keys(g)[0]], 0);
          const percentage = ((genreCount / totalGenresCount) * 100).toFixed(2);

          return (
            <div key={index} className="mb-2 text-[#cdd6f4]">
              <span className="text-[#b4befe] font-semibold">{genreName}</span> - {percentage}%
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default TopGenres;

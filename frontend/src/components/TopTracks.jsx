import React, { useState, useEffect } from 'react';

function TopTracks() {
  const [timeRange, setTimeRange] = useState('short_term');
  const [topTracks, setTopTracks] = useState([]);
  const [playlistCreated, setPlaylistCreated] = useState(false);
  const [playlistId, setPlaylistId] = useState('');
  const [matchPercentage, setMatchPercentage] = useState(0);

  const handleTimeRangeChange = (range) => {
    setTimeRange(range);
    setPlaylistCreated(false); // reset the playlist creation state
    setPlaylistId('');
  };

  useEffect(() => {
    fetchTopTracks();
    fetchMatchPercentage();
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

  const fetchMatchPercentage = async () => {
    try {
      const response = await fetch(`http://localhost:8080/match-percentage?timeRange=${timeRange}`);
      const data = await response.json();
      setMatchPercentage(data);
    } catch (error) {
      console.error('Error fetching match percentage:', error);
    }
  };

  const createPlaylist = async () => {
    try {
      const response = await fetch('http://localhost:8080/create-playlist-with-top-tracks');
      const data = await response.text();
      setPlaylistCreated(true);
      setPlaylistId(data);
    } catch (error) {
      console.error('Error creating playlist:', error);
    }
  };

  const prop = "mt-4 ml-4 mr-4 text-m text-[#cdd6f4] py-1 px-3 rounded-lg";

  // get the appropriate time range label
  const getTimeRangeLabel = () => {
    if (timeRange === 'short_term') {
      return 'these past weeks';
    } else if (timeRange === 'medium_term') {
      return 'in the last six months';
    } else if (timeRange === 'long_term') {
      return 'long term';
    }
    return '';
  };

  // get the match percentage message based on the percentage value
  const getMatchPercentageMessage = (percentage) => {
    if (percentage >= 0 && percentage <= 25) {
      return 'Your music is not very popular.';
    } else if (percentage > 25 && percentage <= 50) {
      return 'Your music seems somewhat popular.';
    } else if (percentage > 50) {
      return 'Your music is popular among Spotify users.';
    }
    return '';
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-[#1e1e2e]">
      <div className="mb-8">
        {/* time range buttons */}
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
        {/* create playlist button */}
        {!playlistCreated && (
          <button
            onClick={createPlaylist}
            className="text-[#cdd6f4] underline mb-4"
          >
            Create a playlist with your top tracks {getTimeRangeLabel()}
          </button>
        )}

        {/* [[laylist creation message */}
        {playlistCreated && (
          <div className="text-[#cdd6f4] mb-4">
            Created playlist with ID: {playlistId}
          </div>
        )}

        {/* match percentage message */}
        { (
          <div className="text-[#b4befe] text-center font-semibold mt-4 mb-4">
            {matchPercentage}% of your top songs are in the top 11400 most listened songs on Spotify.
            <br />
            {getMatchPercentageMessage(matchPercentage)}
          </div>
        )}

        {/* display the top tracks */}
        {topTracks.map((track) => (
          <div key={track.id} className="flex items-center mb-8 text-[#cdd6f4]">
            <a href={track.songUrl}>
              <img src={track.imageUrl} alt={track.name} className="w-10 h-10 mr-4 object-cover rounded" />
            </a>
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

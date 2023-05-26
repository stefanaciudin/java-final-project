import React, { useState, useEffect } from 'react';

function PlaylistPage() {
  const [playlists, setPlaylists] = useState([]);

  useEffect(() => {
    fetchPlaylists();
  }, []);

  const fetchPlaylists = async () => {
    try {
      const response = await fetch('http://localhost:8080/playlists');
      const data = await response.json();
      setPlaylists(data);
    } catch (error) {
      console.error('Error fetching playlists:', error);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-[#1e1e2e]">
      <h3 className="text-[#cdd6f4] text-xl font-semibold mb-4">These are your current playlists:</h3>
      <div className="flex flex-wrap justify-center">
        {playlists.map((playlist) => (
          <div key={playlist.name} className="flex flex-col items-center m-4 text-[#cdd6f4]">
            <a href={playlist.playlistUrl}>
            <img
              src={playlist.imageUrl}
              alt={playlist.name}
              className="w-32 h-32 object-cover rounded"
            />
            </a>
            <div className="mt-2 text-center">
              <div style={{ width: '200px' }}>{playlist.name}</div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default PlaylistPage;

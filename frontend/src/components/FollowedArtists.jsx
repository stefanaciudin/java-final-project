import React, { useEffect, useState } from 'react';

function FollowedArtists() {
  const [followedArtists, setFollowedArtists] = useState([]);

  useEffect(() => {
    const fetchFollowedArtists = async () => {
      try {
        const response = await fetch('http://localhost:8080/followed-artists');
        const data = await response.json();
        setFollowedArtists(data);
      } catch (error) {
        console.error('Error fetching followed artists:', error);
      }
    };

    fetchFollowedArtists();
  }, []);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-[#1e1e2e]">
      <h2 className="text-[#cdd6f4] text-xl font-semibold">Your Followed Artists</h2>
      <div className="flex flex-wrap justify-center mt-4">
        {/* display followed artists */}
        {followedArtists.map((artist) => (
          <div key={artist.name} className="m-4">
            <a href={artist.artistUrl}>
              <img src={artist.imageUrl} alt={artist.name} className="w-40 h-40 object-cover rounded-full" />
            </a>
            <p className="text-[#cdd6f4] mt-2">{artist.name}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default FollowedArtists;

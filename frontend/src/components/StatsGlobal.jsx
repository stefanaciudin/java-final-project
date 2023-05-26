import React from "react";
import Top from "./Top";

function StatsGlobal() {
    return (
        <div className="experience flex flex-col items-center justify-start px-[5rem] bg-[#181825] h-[60rem] pt-[18rem] mt-[-10rem] relative z-[2] rounded-b-[5rem]">
            <img src={require("../img/wind.png")} alt="" className="w-[5rem]" />
            {/* heading */}
            <div className="headline mt-7 flex flex-col items-center text-[2rem] text-[#cdd6f4]">
                <span>Curious about some stats from 2022?</span>
                <span>
                    <b>There you go.</b>
                </span>
            </div>
            {/* top */}
            <div className="feature flex items-center justify-around mt-[6rem] w-[100%]">
                <Top icon="spotify"
                    title="Top throwbacks of 2022"
                    content="The songs with the biggest throwbacks."
                    link="https://open.spotify.com/playlist/37i9dQZF1DXdpy4ZQQMZKm?si=7861b6717aa948c8"
                    artists={[
                        "Running Up That Hill - Kate Bush",
                        "Die For You - The Weeknd",
                        "Another Love - Tom Odell",
                        "One Kiss - Calvin Harris, Dua Lipa",
                        "Infinity - Jaymes Young"
                    ]} />
                <Top icon="sound-waves" title="Songs That Made Us Cry in 2022" content="Songs that stuck by us through tears and heartbreak." link="https://open.spotify.com/playlist/37i9dQZF1DXdTIvz6bRLvq?si=22a8f81facdc442f" artists={[
                    "Glimpse of Us - Joji",
                    "All Too Well (10 Minute Version) - Taylor Swift",
                    "Fingers Crossed - Lauren Spencer Smith",
                    "How Do I Say Goodbye - Dean Lewis",
                    "Easy On Me - Adele"
                ]} />
                <Top icon="spotify" title="Viral Rock Hits 2022" content="Rock tracks that took off in 2022." link="https://open.spotify.com/playlist/37i9dQZF1DWSAm0NxvFu7q?si=00c37aa0c699440a" artists={[
                    "Master Of Puppets - Metallica",
                    "Mary On A Cross - Ghost",
                    "Running Up That Hill - Kate Bush",
                    "Teenage Dirtbag - Wheatus",
                    "Misery Business - Paramore"
                ]} />
            </div>
        </div>
    );
}

export default StatsGlobal;

import { React, useState } from "react";
import VisibilitySensor from "react-visibility-sensor";
import Top from "./Top";


function StatsUser() {
    const [elementIsVisible, setElementIsVisible] = useState(false);
    const buttonStyle = "mt-[20px] text-[#1e1e2e] text-[15px] mr-[185px] border-[2px] rounded-[10px] border-[#cba6f7] px-[85px] py-[17px] bg-[#cba6f7]";
    return (
        <VisibilitySensor
            onChange={(isVisible) => setElementIsVisible(isVisible)}
            minTopValue={300}
        >
            <div>
                <div className="text-[#cdd6f4] wrapper bg-[#1e1e2e] flex items-center justify-between px-[5rem] rounded-b-[5rem] w-[100%] h-[35rem] relative z-[3]">
                    {/* left side */}
                    <div className="headings flex flex-col items-start justify-center h-[100%] text-[3rem]">
                        <span>What are you curious about?</span>{" "}
                        <span>
                            <b>You pick bestie.</b>
                        </span>
                        <span className="text-[20px] text-[#bac2de] text-left">
                            Browse and find out your most listened songs,
                            <br />
                            your top genres, or your top artists.
                        </span>
                    </div>
                    {/* right side */}
                    <div className="buttons flex flex-col items-center">
                        <a href="http://localhost:8080/top-tracks">
                            <button className={buttonStyle}>Top Tracks</button>
                        </a>
                        <a href="http://localhost:8080/artists">
                        <button className={buttonStyle}>Top Artists</button>
                        </a>
                        <a href="http://localhost:8080/top-genres">
                        <button className={buttonStyle}>Top Genres</button>
                        </a>
                    </div>
                </div>
                <div className="experience flex flex-col items-center justify-start px-[5rem] bg-[#181825] h-[60rem] pt-[18rem] mt-[-10rem] relative z-[2] rounded-b-[5rem]">
                    <img src={require("../img/wind.png")} alt="" className="w-[5rem]" />
                    {/* heading */}
                    <div className="headline mt-7 flex flex-col items-center text-[2rem] text-[#cdd6f4]">
                        <span>Daily reminder on what you can see here.</span>
                        <span>
                            <b>There you go.</b>
                        </span>
                    </div>
                    {/* info*/}
                    <div className="feature flex items-center justify-around mt-[6rem] w-[100%]">
                        <Top icon="calendar" title="Custom time ranges"
                            content="See your recent data or your data from the past 6 months.
                        to you."/>

                        <Top icon="playlist" title="Create playlists"
                            content="Create playlists with your most listened songs.
                        Or, if you're feeling brave, we can make you a playlist
                        with suggested artists based on your listening history."/>
                        <Top icon="time" title="Updated daily"
                            content="Your data is updated daily, so you can always 
                            come back and check again."
                        />

                    </div>

                </div>
            </div>
        </VisibilitySensor>
    );
}



export default StatsUser
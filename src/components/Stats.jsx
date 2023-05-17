import React from "react";
import Top from "./Top";

function Stats() {
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
                <Top icon="spotify" title="Top throwbacks of 2022" />
                <Top icon="sound-waves" title="Top songs of 2022" />
                <Top icon="spotify" title="Alt top 2022 idk" />
            </div>
        </div>
    );
}

export default Stats;

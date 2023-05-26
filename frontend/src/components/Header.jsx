import React from "react";

function Header() {
    return (
        <div className="header bg-[#1e1e2e] flex items-center justify-between px-[5rem] pt-[2.4rem] text-[0.8rem]">
            {/* logo */}
            <img src={require("../img/logo.png")} alt="" className="logo w-[42px] h-[42px]" />

            {/* home icon */}
            <a href="/">
                <img
                    src={require("../img/home.png")}
                    alt="Home"
                    className="home-icon w-[42px] h-[42px]"
                />
            </a>

        </div>
    );
}

export default Header;

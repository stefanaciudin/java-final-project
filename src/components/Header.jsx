import React from "react";

function Header() {
    return (
        <div className="header bg-[#1e1e2e] flex items-center justify-between px-[5rem] pt-[2.4rem] text-[0.8rem]">
            {/* logo */}
            <img
                src={require("../img/logo.png")}
                alt=""
                className="logo  w-[42px] h-[42px]"
            />
            {/* side menu */}
            {/* <CenterMenu /> */}
            {/* buttons */}
        </div>
    );
}

export default Header;
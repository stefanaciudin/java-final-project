import { React, useState } from "react";
import VisibilitySensor from "react-visibility-sensor";
import { motion } from "framer-motion";

function Comp1() {
    const [elementIsVisible, setElementIsVisible] = useState(false);
    const bg = {
        true: {
            left: "7rem",
        },
        false: {
            left: "19rem",
        },
    };
    const musicPlayer = {
        true: {
            left: "295px",
        },
        false: {
            left: "235px",
        },
    };
    const rect = {
        true: {
            left: "11rem",
        },
        false: {
            left: "13rem",
        },
    }
    const heart = {
        true: {
            left: "9rem",
        },
        false: {
            left: "12.5rem",
        },
    };
    return (
        <VisibilitySensor
            onChange={(isVisible) => setElementIsVisible(isVisible)}
            minTopValue={300}
        >
            <div className="text-[#cdd6f4] wrapper bg-[#1e1e2e] flex items-center justify-between px-[5rem] rounded-b-[5rem] w-[100%] h-[35rem] relative z-[3]">
                {/* left side */}
                <div className="headings flex flex-col items-start justify-center h-[100%] text-[3rem]">
                    <span>Ever wondered</span>{" "}
                    <span>
                        <b>who you really are?</b>
                    </span>
                    <span className="text-[20px] text-[#bac2de] text-left">
                        Tune in and find out.
                        <br />
                        All you have to do is login with your Spotify account.
                        <br />
                        Or sign up. It's really cool.
                    </span>
                    <div className="buttons flex">
                        <button className={" mt-[20px] text-[15px] mr-[35px] hover:bg-[#cba6f7] border-[2px] rounded-[10px] border-[#cba6f7] px-[25px] py-[7px]"}>
                            Log in
                        </button>
                        <button className={"mt-[20px] text-[#1e1e2e] text-[15px] mr-[35px] hover:bg-[#cba6f7] border-[2px] rounded-[10px] border-[#cba6f7] px-[25px] py-[7px] bg-[#cba6f7]"}>
                            Sign up
                        </button>
                    </div>
                </div>
                {/* right side */}
                <div className="images relative w-[50%]">
                    <motion.img
                        variants={bg}
                        animate={`${elementIsVisible}`}
                        transition={{ duration: 1, type: "ease-out" }}
                        alt=""
                        className="absolute top-[-8rem] left-[19rem]"
                    />
                    <img
                        src={require("../img/p 1.png")}
                        alt=""
                        className="absolute top-[-15rem] h-[34rem] left-[13rem]"
                    />
                    <motion.img
                        variants={musicPlayer}
                        animate={`${elementIsVisible}`}
                        transition={{
                            duration: 1,
                            type: "ease-out",
                        }}
                        src={require("../img/p 2.png")}
                        alt=""
                        className="absolute left-[230px] top-[94px] w-[175px]"
                    />
                    <motion.img
                        variants={rect}
                        animate={`${elementIsVisible}`}
                        transition={{
                            type: "ease-out",
                            duration: 1,
                        }}
                        src={require("../img/p 3.png")}
                        alt=""
                        className="absolute w-[5rem] left-[13rem] top-[12rem]"
                    />
                    <motion.img
                        variants={heart}
                        animate={`${elementIsVisible}`}
                        transition={{
                            type: "ease-out",
                            duration: 1,
                        }}
                        src={require("../img/p 4.png")}
                        alt=""
                        className="absolute w-[5rem] left-[12.5rem] top-[12rem]"
                    />
                </div>
            </div>
        </VisibilitySensor>
    );
}

export default Comp1;

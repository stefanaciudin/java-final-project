import { React, useState } from "react";
import { motion } from "framer-motion";
import VisibilitySensor from "react-visibility-sensor";

function Top({ icon, title, content, link, artists }) {
    const variant = {
        true: {
            transform: "scale(1)",
        },
        false: {
            transform: "scale(0.5)",
        },
    };
    const [elementIsVisible, setElementIsVisible] = useState(false);

    return (
        <VisibilitySensor
            onChange={(isVisible) => setElementIsVisible(isVisible)}
        >
            <div className="feature flex items-center justify-center flex-col relative text-center mx-12 text-[#bac2de]">
                {/* icon */}
                <motion.div
                    variants={variant}
                    transition={{
                        duration: 1,
                        type: "ease-out",
                    }}
                    animate={`${elementIsVisible}`}
                    className="icon bg-[#1e1e2e] rounded-2xl p-4"
                >
                    <img
                        src={require(`../img/${icon}.png`)}
                        alt=""
                        className="w-[3rem]"
                    />
                </motion.div>

                <span className="mt-5"><b>{title}</b></span>

                <span className="text-[#a6adc8] mt-4">
                    {content}
                </span>

                <ul className="text-[#a6adc8] mt-4">
                    {/* {console.log(artists)} */}
                    {artists.slice(0, 5).map((artist, index) => (
                        <div key={index}>
                            {`${index + 1}. ${artist}`}
                        </div>
                    ))}
                    {/* {artists} */}
                </ul>

                <a href={link}>
                    <span className="text-[#7f849c] underline hover:cursor-pointer">
                        Try it here 
                    </span>
                </a>
            </div>
        </VisibilitySensor>
    );
}

export default Top;

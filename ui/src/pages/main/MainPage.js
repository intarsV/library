import React from 'react'


const MainPage=()=>{


    return (
        <div className="card">
            <h4 className="header-padding">Welcome!</h4>
            <br/>
            <h5>This is my homework project - Library management.<br/>
                - techStack -> SpringBoot, Spring Security and React;<br/>
                - two user authorities -> "ADMIN" and "USER";<br/>
                <div className="card">
                    <h6 className="text-size">
                        ADMIN: login: "initex", pass: "initex000";<br/>
                        <br/>
                        USER: login: "ivars", pass: "ivars000";<br/>
                        USER: login: "ritvars", pass: "ritvars000";<br/>
                    </h6>
                </div>
            </h5>
        </div>
    )

};

export default MainPage;
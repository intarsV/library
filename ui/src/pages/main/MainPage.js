import React from 'react'
import {Card} from "react-bootstrap";

const MainPage=()=>{


    return (
        <div className="card-padding">
            <Card>
                <h4>Welcome!</h4>
                <br/>
                <h5>This is my homework project - Library management.<br/>
                    - techStack -> SpringBoot, Spring Security and React;<br/>
                    - two user authorities -> "ADMIN" and "USER";<br/>
                   <div className="small-card-padding ">
                    <Card>
                        <h6 className="text-size">
                            ADMIN:  login: "initex", pass: "initex000";<br/>
                            <br/>
                            USER: login: "ivars", pass: "ivars000";<br/>
                            USER: login: "ritvars", pass: "ritvars000";<br/>
                        </h6>
                    </Card>
                   </div>
                </h5>
            </Card>
        </div>
    )

};

export default MainPage;
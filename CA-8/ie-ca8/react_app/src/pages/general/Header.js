import React from "react";
import "../../Assets/styles/header.css";
import Modal from 'react-bootstrap/Modal';
import LOGO from "../../Assets/images/LOGO.png";
import {Link} from "react-router-dom";

export default class Header extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render() {
        return (
            <div>
                <header>
                    <div className="header">
                        <div className="bolbol-icon m-2">
                            <Link to="/home">
                                <img alt="" src={LOGO}></img>
                            </Link>
                        </div>
                        <div className="profile m-2">
                            <Link to="/home">
                                خانه
                            </Link>
                        </div>
                        <div className="profile ml-auto m-2">
                            <Link to="/plan">
                                برنامه هفتگی
                            </Link>
                        </div>
                        <div className="logout m-2">
                            <a href="#modal" style={{color: 'inherit'}}>
                                خروج
                                <i className="flaticon-log-out"></i>
                            </a>
                        </div>
                    </div>
                </header>
            </div>
        );
    }
}
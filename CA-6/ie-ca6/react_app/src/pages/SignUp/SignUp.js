import * as React from "react";
import SignUpService from "../../services/SignUpService";
import "../../Assets/styles/signup-styles.css";
import PropTypes, {shape} from "prop-types";
import {Link} from "react-router-dom";
import CoverPhoto from '../../Assets/images/CoverPhoto.jpg';

export default class SignUp extends React.Component {

    constructor(props) {
        super(props);
        this.state = {}
    }

    componentDidMount() {
        document.title = "Create your Account";
    }

    render() {
        return (
            <div className="main-box w-100 h-100">
                <div className="d-flex flex-row-reverse no-gutters h-100">
                    <div className="col-lg-9 col-md-8 h-100">
                        <img className="background-img" src={CoverPhoto} alt=""/>
                    </div>
                    <div className="col-lg-3 col-md-4 overflow-auto">
                        <div className="form-container">
                            <div className="d-flex">
                                <h1>
                                    ثبت‌نام
                                </h1>
                            </div>
                            <div className="d-flex justify-content-center align-items-center ">
                                <form action="tmp" className=" w-100">
                                    <div className="">
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="fullname">
                                                    نام کامل
                                                </label>
                                            </div>
                                            <input type="text" name="fullname" id="fullname" placeholder="نام"/>
                                        </div>
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="mail">
                                                    ایمیل
                                                </label>
                                            </div>
                                            <input type="text" name="mail" id="mail" placeholder="ایمیل"/>
                                        </div>
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="password">
                                                    رمز عبور
                                                </label>
                                            </div>
                                            <input type="password" name="password" id="password"
                                                   placeholder="***********"/>
                                        </div>
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="repeatPassword">
                                                    تکرار رمز عبور
                                                </label>
                                            </div>
                                            <input type="password" name="password" id="repeatPassword"
                                                   placeholder="***********"/>
                                        </div>
                                        <div className="form-submit row">
                                            <div className="col-auto">
                                                <input type="submit" value="ثبت‌نام"/>
                                            </div>
                                            <div
                                                className="col-auto d-flex justify-content-center align-items-center clickable">
                                                <div className="login-arrow">
                                                    <i className="flaticon-arrow"></i>
                                                </div>
                                                <Link to="/login">
                                                    ورود
                                                </Link>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
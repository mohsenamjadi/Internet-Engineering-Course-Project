import * as React from "react";
import LoginService from "../../services/LoginService";
import "../../Assets/styles/login-styles.css";
import PropTypes, {shape} from "prop-types";
import {Link} from "react-router-dom";
import CoverPhoto from '../../Assets/images/CoverPhoto.jpg';

export default class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            email: null
        }

        this.handleLogin = this.handleLogin.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
        document.title = "Sign in - Accounts";
    }

    async handleLogin() {
        await LoginService.login(this.state.email);
    }

    handleChange(event) {
        event.preventDefault();
        this.setState({email: event.target.value});
        event.preventDefault();
    }

    render() {
        return (
            <div className="main-box w-100 h-100">
                <div className="d-flex flex-row-reverse no-gutters h-100">
                    <div className="col-lg-9 col-md-8 h-100">
                        <img className="background-img" src={CoverPhoto} alt="" />
                    </div>
                    <div className="col-lg-3 col-md-4 overflow-auto">
                        <div className="form-container">
                            <div className="d-flex">
                                <h1>
                                    ورود
                                </h1>
                            </div>
                            <div className="d-flex justify-content-center align-items-center ">
                                <form action="tmp" className=" w-100">
                                    <div className="">
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="mail">
                                                    ایمیل
                                                </label>
                                            </div>
                                            <input type="text" name="mail" id="mail" placeholder="ایمیل" onChange={this.handleChange}/>
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
                                        <div className="form-submit row">
                                            <div className="col-auto">
                                                <input type="submit" onClick={this.handleLogin}  value="ورود"/>
                                            </div>
                                            <div
                                                className="col-auto d-flex justify-content-center align-items-center clickable">
                                                <div className="login-arrow">
                                                    <i className="flaticon-arrow"></i>
                                                </div>
                                                <Link to="/signup">
                                                    ثبت‌نام
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
import * as React from "react";
import "../../Assets/styles/login-styles.css";
import {Link} from "react-router-dom";
import CoverPhoto from '../../Assets/images/CoverPhoto.jpg';
import AuthService from "../../services/AuthService";
import {toast} from "react-toastify";


export default class ForgetPassword extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            email: "",
            isLoading: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.validateForm = this.validateForm.bind(this);
    }

    componentDidMount() {
        document.title = "Forget Password - Accounts";
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        this.setState({ [name]: value });
    }

    handleSubmit(event) {
        this.setState({
            isLoading: true
        });
        event.preventDefault();
        let userForm = {
            email: this.state.email,
        };

        AuthService.forgetPassword(userForm).then(() => {
            toast.success("عملیات با موفقیت انجام شد");
            this.setState({
                isLoading: false
            });
        }).catch(e => {
            this.setState({
                isLoading: false
            });
            toast.error("کاربری با این ایمیل ثبت نام نکرده است.");
        })
    }

    validateForm() {
        return (
            this.state.email.length > 0
        );
    }

    render() {
        return (
            <div className="main-box w-100 h-100">
                <div className="d-flex flex-row-reverse no-gutters h-100">
                    <div className="col-lg-9 col-md-8 h-100">
                        <img className="background-img" src={CoverPhoto} alt="" />
                    </div>
                    <div className="col-lg-3 col-md-4 overflow-auto">
                        <div className="form-container align-items-center">
                            <div className="d-flex">
                                <h1>
                                    فراموشی کلمه عبور
                                </h1>
                            </div>
                            <div className="d-flex justify-content-center align-items-center ">
                                <form action="tmp" className=" w-100" onChange={this.handleChange} onSubmit={this.handleSubmit}>
                                    <div className="">
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="email">
                                                    ایمیل
                                                </label>
                                            </div>
                                            <input type="email" name="email" id="email" placeholder="ایمیل" />
                                        </div>
                                        <div className="form-submit row">
                                            <div className="col-auto">
                                                <input type="submit" disabled={!this.validateForm()} value="ارسال" />
                                                {this.state.isLoading &&
                                                <span className="spinner-border mr-2" role="status" aria-hidden="true"/>
                                                }
                                            </div>
                                            <div
                                                className="col-auto d-flex justify-content-center align-items-center clickable">
                                                <div className="login-arrow">
                                                    <i className="flaticon-arrow"/>
                                                </div>
                                                <span>
                                                    <Link to="/signup">
                                                        ثبت‌نام
                                                    </Link>
                                                </span>
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
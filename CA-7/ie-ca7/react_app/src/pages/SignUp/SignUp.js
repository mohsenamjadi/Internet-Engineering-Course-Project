import * as React from "react";
import "../../Assets/styles/signup-styles.css";
import {Link} from "react-router-dom";
import CoverPhoto from '../../Assets/images/CoverPhoto.jpg';
import AuthService from "../../services/AuthService";
import {toast} from "react-toastify";

export default class SignUp extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            id: "",
            name: "",
            secondName: "",
            birthDate: "",
            field: "",
            faculty: "",
            level: "",
            email: "",
            password: "",
            repeatPassword: "",
            isLoading: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.validateForm = this.validateForm.bind(this);
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
            id: this.state.id,
            name: this.state.name,
            secondName: this.state.secondName,
            birthDate: this.state.birthDate,
            field: this.state.field,
            faculty: this.state.faculty,
            level: this.state.level,
            email: this.state.email,
            password: this.state.password
        };
        AuthService.signup(userForm).then(data => {
            toast.success("ثبت نام با موفقیت انجام شد.");
            this.setState({
                isLoading: false
            });
            console.log(data.data);
            let bearerToken = data.data;
            let token = bearerToken.slice(7, bearerToken.length);
            console.log(token);
            localStorage.setItem("token", token);
            window.location = "/";
        }).catch(e => {
            this.setState({
                isLoading: false
            });
            console.log(e);
            toast.error("کاربری با این شماره دانشجویی/ایمیل پیش تر ثبت نام شده است.");
        })
    }

    componentDidMount() {
        document.title = "Create your Account";
    }

    validateForm() {
        return (
            this.state.id.length > 0 &&
            this.state.name.length > 0 &&
            this.state.secondName.length > 0 &&
            this.state.field.length > 0 &&
            this.state.faculty.length > 0 &&
            this.state.level.length > 0 &&
            this.state.email.length > 0 &&
            this.state.password.length > 0 &&
            this.state.repeatPassword.length > 0 &&
            this.state.password === this.state.repeatPassword
        );
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
                                <form action="tmp" className=" w-100" onSubmit={this.handleSubmit} onChange={this.handleChange}>
                                    <div className="">
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="name">
                                                    نام
                                                </label>
                                            </div>
                                            <input type="text" name="name" id="name" placeholder="نام"/>
                                        </div>
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="secondName">
                                                    نام‌خانوادگی
                                                </label>
                                            </div>
                                            <input type="text" name="secondName" id="secondName" placeholder="نام‌خانوادگی"/>
                                        </div>
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="id">
                                                    شماره دانشجویی
                                                </label>
                                            </div>
                                            <input type="number" name="id" id="id" placeholder="شماره دانشجویی"/>
                                        </div>
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="birthDate">
                                                    تاریخ تولد
                                                </label>
                                            </div>
                                            <input type="date" name="birthDate" id="birthDate" />
                                        </div>
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="field">
                                                    رشته
                                                </label>
                                            </div>
                                            <input type="text" name="field" id="field" placeholder="رشته"/>
                                        </div>
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="faculty">
                                                    دانشکده
                                                </label>
                                            </div>
                                            <input type="text" name="faculty" id="faculty" placeholder="دانشکده"/>
                                        </div>
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="level">
                                                    مقطع
                                                </label>
                                            </div>
                                            <input type="text" name="level" id="level" placeholder="مقطع"/>
                                        </div>
                                        <div className="form-item">
                                            <div className="d-flex">
                                                <label htmlFor="email">
                                                    ایمیل
                                                </label>
                                            </div>
                                            <input type="email" name="email" id="email" placeholder="ایمیل"/>
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
                                            <input type="password" name="repeatPassword" id="repeatPassword"
                                                   placeholder="***********"/>
                                        </div>
                                        <div className="form-submit row">
                                            <div className="col-auto">

                                                <input type="submit" disabled={!this.validateForm()} value="ثبت‌نام"/>
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
                                                    <Link to="/login">
                                                         ورود
                                                    </Link>
                                                </span>
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
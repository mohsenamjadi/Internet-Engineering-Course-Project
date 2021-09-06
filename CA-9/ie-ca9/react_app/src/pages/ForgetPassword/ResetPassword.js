import * as React from "react";
import "../../Assets/styles/login-styles.css";
import CoverPhoto from '../../Assets/images/CoverPhoto.jpg';
import AuthService from "../../services/AuthService";
import {toast} from "react-toastify";
import { useQueryParam, NumberParam, StringParam } from 'use-query-params';

function injectTodos(Component) {
    const InjectedTodos = function (props) {
        const [token, setToken] = useQueryParam('token', StringParam);
        return <Component {...props} token={token} setToken={setToken} />;
    };
    return InjectedTodos;
}

class ResetPassword extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            password: "",
            repeatPassword: "",
            isLoading: false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.validateForm = this.validateForm.bind(this);
    }

    componentDidMount() {
        document.title = "Reset Password - Accounts";
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
            password: this.state.password,
            repeatPassword: this.state.repeatPassword
        };

        AuthService.resetPassword(userForm, this.props.token).then(() => {
            toast.success("عملیات با موفقیت انجام شد");
            this.setState({
                isLoading: false
            });
        }).catch(e => {
            this.setState({
                isLoading: false
            });
            toast.error("مشکلی پیش آمده! لطفا دوباره تلاش کنید");
        })
    }

    validateForm() {
        return (
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
                        <img className="background-img" src={CoverPhoto} alt="" />
                    </div>
                    <div className="col-lg-3 col-md-4 overflow-auto">
                        <div className="form-container align-items-center">
                            <div className="d-flex">
                                <h1>
                                    بازنشانی کلمه عبور
                                </h1>
                            </div>
                            <div className="d-flex justify-content-center align-items-center ">
                                <form action="tmp" className=" w-100" onChange={this.handleChange} onSubmit={this.handleSubmit}>
                                    <div className="">
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
                                                <input type="submit" disabled={!this.validateForm()} value="اعمال" />
                                                {this.state.isLoading &&
                                                <span className="spinner-border mr-2" role="status" aria-hidden="true"/>
                                                }
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


export default injectTodos(ResetPassword);
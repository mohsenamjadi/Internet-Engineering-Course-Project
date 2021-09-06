import * as React from "react";
import Profile from "./Profile";
import Karname from "./Karname";
import HomeTopSection from "./HomeTopSection";
import Footer from "../general/Footer";
import "../../Assets/styles/Myhome-styles.css";
import HomeService from "../../services/HomeService";
import LOGO from "../../Assets/images/LOGO.png";
import {Link} from "react-router-dom";
import AuthService from "../../services/AuthService";

export default class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            student: null,
            show: false
        };
    }

    componentDidMount() {
        document.title = "Home Page";

        this.getProfileInfo();
    }

    async getProfileInfo() {
        let studentInfo = await HomeService.getStudentProfile();
        console.log(studentInfo);
        this.setState(
            {
                student: studentInfo
            }
        )
    }

    render() {
        return (
            <div className="h-100">
                {this.state.student !== null ?
                    this.renderPage()
                    :
                    this.renderLoading()
                }
            </div>
        );
    }

    renderLoading() {
        return (
            <div className="h-100 m-5 center-text p-5">
                <div className="spinner-grow text-danger m-5" role="status">
                    <span className="sr-only">Loading...</span>
                </div>
            </div>
        );
    }

    renderHeader() {
        return (
            <div>
                <header>
                    <div className="header">
                        <div className="bolbol-icon m-2">
                            <Link to="/home">
                                <img alt="" src={LOGO}/>
                            </Link>
                        </div>
                        <div className="profile m-2">
                            <Link to="/courses">
                                انتخاب واحد
                            </Link>
                        </div>
                        <div className="profile ml-auto m-2">
                            <Link to="/plan">
                                برنامه هفتگی
                            </Link>
                        </div>
                        <div className="logout m-2">
                            <a href='#modal' style={{color: 'inherit'}} >
                                خروج
                                <i className="flaticon-log-out"></i>
                            </a>
                        </div>
                    </div>
                </header>
            </div>
        )
    }

    handleLogout = async () => {
        await AuthService.logout();
    }

    renderPage() {
        return (
            <body>
            {this.renderHeader()}

            <HomeTopSection/>

            <main>
                <div className="row mt-5">
                    <Profile
                        student={this.state.student}
                    />

                    <Karname
                        terms={this.state.student.terms}
                    />
                </div>

                <div className="md-modal" id="modal">
                    <div className="md-content">
                        <div className="row no-gutters" style={{marginBottom: '30px'}}>
                            <span style={{fontSize: 'larger', fontWeight: 'bold'}}>
                                آیا می خواهید از حساب کاربری خود خارج شوید؟
                            </span>
                        </div>

                        <div className="row no-gutters">
                            <div className="col-2 mr-auto m-2">
                                <a href='#' style={{textDecoration: 'none'}}>
                                    <button className="btn-kenseraf">انصراف</button>
                                </a>
                            </div>
                            <div className="col-2 m-2">
                                <a href='/login' style={{textDecoration: 'none'}}>
                                    <button onClick={this.handleLogout} className="btn-khorooj">خروج</button>
                                </a>
                            </div>
                        </div>

                    </div>
                </div>
                <div className="md-overlay"></div>


            </main>

            <Footer/>
            </body>
        );
    }
}
import React from "react";
import Footer from "../general/Footer";
import PlanService from "../../services/PlanService";
import "../../Assets/styles/WeeklySchedule.css";
import "../../Assets/styles/Myhome-styles.css";
import {Link} from "react-router-dom";
import LOGO from "../../Assets/images/LOGO.png";
import "../../Assets/styles/header.css";
import HomeService from "../../services/HomeService";
import Translator from "../../utils/Translator";
import AuthService from "../../services/AuthService";

export default class WeeklyPlan extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            weeklyScheduleItems: null
        };
    }

    componentDidMount() {
        this.getWeeklyScheduleItems();
    }

    async getWeeklyScheduleItems() {
        let weeklyScheduleItems = await PlanService.getWeeklySchedule();
        console.log(weeklyScheduleItems);
        this.setState(
            {weeklyScheduleItems: weeklyScheduleItems}
        )
    }

    handleLogout = async () => {
        await AuthService.logout();
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

    render() {
        return (
            <>
                {this.state.weeklyScheduleItems !== null ?
                    this.renderPage()
                    :
                    this.renderLoading()
                }
            </>
        );
    }

    randomClass(){
        let color = Math.floor(Math.random() * 4) + 1;

        switch (color) {
            case 1:
                return "accent-pink-gradient";
            case 2:
                return "accent-orange-gradient";
            case 3:
                return "accent-green-gradient";
            case 4:
                return "accent-blue-gradient";
        }
    }

    renderSlot(day, time){
        if(this.func(day, time) !== undefined)
            return(
                // <div className={this.randomClass()} style={{height: '100%'}}>
                <div className="accent-pink-gradient" style={{height: '100%'}}>
                    <div className="col">
                        <div>
                            {
                                this.func(day, time) !== undefined ?
                                    this.func(day, time).classTime.time
                                    :
                                    ""
                            }
                        </div>
                        <div>
                            {
                                this.func(day, time) !== undefined ?
                                    this.func(day, time).name
                                    :
                                    ""
                            }
                        </div>
                        <div>
                            {
                                this.func(day, time) !== undefined ?
                                    Translator.toFa(Translator.toFa(this.func(day, time).type))
                                    :
                                    ""
                            }
                        </div>
                    </div>
                </div>
            );
        else
            return "";
    }

    func(day, time){
        let weeklyScheduleItem = this.state.weeklyScheduleItems.
        find(weeklyScheduleItem => weeklyScheduleItem.offering.classTime.days.includes(day) && weeklyScheduleItem.offering.classTime.time == time)

        if(weeklyScheduleItem !== undefined)
            return weeklyScheduleItem.offering
        else
            return undefined;
    }

    renderHeader() {
        return(
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
                        <Link to="/courses">
                            انتخاب واحد
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
        );
    }

    renderPage() {
        return (
            <div>
                {this.renderHeader()}

                <main>
                    <div className="timetable" style={{paddingTop: '60px'}}>
                        <div className="selector-upper row no-gutters" style={{top: '0px', height: '60px'}}>
                            <div className="ml-auto" style={{marginRight: '15px'}}>
                                <span className="calender-icon">
                                    <i className="flaticon-calendar"></i>
                                </span>
                                <span className="schedule-title">برنامه هفتگی</span>
                            </div>
                            <div style={{marginLeft: '15px'}}>
                                <span className="schedule-term-num">ترم ۶</span>
                            </div>
                        </div>
                        <div className="week-names">
                            <div>شنبه</div>
                            <div>یک شنبه</div>
                            <div>دوشنبه</div>
                            <div>سه شنبه</div>
                            <div>چهارشنبه</div>
                            <div className="weekend">پنج شنبه</div>
                            <div className="weekend">جمعه</div>
                        </div>
                        <div className="time-interval">
                            <div>۹:۰۰ - ۷:۳۰</div>
                            <div>۱۰:۳۰ - ۹:۰۰</div>
                            <div>۱۲:۰۰ - ۱۰:۳۰</div>
                            <div>۱۵:۳۰ - ۱۴:۰۰</div>
                            <div>۱۷:۳۰ - ۱۶:۰۰</div>
                        </div>
                        <div className="content">
                            <div>
                                {this.renderSlot("Saturday", "7:30-9:00")}
                            </div>
                            <div>
                                {this.renderSlot("Sunday", "7:30-9:00")}
                            </div>
                            <div>
                                {this.renderSlot("Monday", "7:30-9:00")}
                            </div>
                            <div>
                                {this.renderSlot("Tuesday", "7:30-9:00")}
                            </div>
                            <div>
                                {this.renderSlot("Wednesday", "7:30-9:00")}
                            </div>
                            <div className="weekend"></div>
                            <div className="weekend"></div>
                            <div>
                                {this.renderSlot("Saturday", "9:00-10:30")}
                            </div>
                            <div>
                                {this.renderSlot("Sunday", "9:00-10:30")}
                            </div>
                            <div>
                                {this.renderSlot("Monday", "9:00-10:30")}
                            </div>
                            <div>
                                {this.renderSlot("Tuesday", "9:00-10:30")}
                            </div>
                            <div>
                                {this.renderSlot("Wednesday", "9:00-10:30")}
                            </div>
                            <div className="weekend"></div>
                            <div className="weekend"></div>
                            <div>
                                {this.renderSlot("Saturday", "10:30-12:00")}
                            </div>
                            <div>
                                {this.renderSlot("Sunday", "10:30-12:00")}
                            </div>
                            <div>
                                {this.renderSlot("Monday", "10:30-12:00")}
                            </div>
                            <div>
                                {this.renderSlot("Tuesday", "10:30-12:00")}
                            </div>
                            <div>
                                {this.renderSlot("Wednesday", "10:30-12:00")}
                            </div>
                            <div className="weekend"></div>
                            <div className="weekend"></div>
                            <div>
                                {this.renderSlot("Saturday", "14:00-15:30")}
                            </div>
                            <div>
                                {this.renderSlot("Sunday", "14:00-15:30")}
                            </div>
                            <div>
                                {this.renderSlot("Monday", "14:00-15:30")}
                            </div>
                            <div>
                                {this.renderSlot("Tuesday", "14:00-15:30")}
                            </div>
                            <div>
                                {this.renderSlot("Wednesday", "14:00-15:30")}
                            </div>
                            <div className="weekend"></div>
                            <div className="weekend"></div>
                            <div>
                                {this.renderSlot("Saturday", "16:00-17:30")}
                            </div>
                            <div>
                                {this.renderSlot("Sunday", "16:00-17:30")}
                            </div>
                            <div>
                                {this.renderSlot("Monday", "16:00-17:30")}
                            </div>
                            <div>
                                {this.renderSlot("Tuesday", "16:00-17:30")}
                            </div>
                            <div>
                                {this.renderSlot("Wednesday", "16:00-17:30")}
                            </div>
                            <div className="weekend"></div>
                            <div className="weekend"></div>
                        </div>
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
            </div>
        );
    }
}
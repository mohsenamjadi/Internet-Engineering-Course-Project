import React from "react";
import CoursesService from "../../services/CoursesService";
import Selecteditem from "./Selecteditem";

export default class CoursesSelected extends React.Component {

    constructor(props) {
        super(props);
        this.state= {
            lenOfSubmited: 0
        }

    }

    componentDidMount() {
        this.lenOfSubmitedCourses();
    }

    async lenOfSubmitedCourses() {
        let sumOfUnits = await CoursesService.getLastSumbitedCoursesUnits();
        console.log(sumOfUnits);
        this.setState({lenOfSubmited : sumOfUnits});
    }

    renderSelectedList() {
        return this.props.selectedCourses.map((courseItem) =>
            <div className="course-item">
                <div className="row no-gutters">
                    <Selecteditem courseItem={courseItem} key={courseItem.code} onDelete={this.props.onDelete}/>
                </div>
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

    renderMain() {
        return (
            <div className="courses-charge-wrapper">
                <div className="courses-charge" style={{padding: "60px 0"}}>
                    <div className="selector row" style={{right: "80px"}}>
                        <div className="selector-item active-item">
                    <span>
                        دروس انتخاب شده
                    </span>
                        </div>
                    </div>
                    <div className="courses" style={{width: "95%" , height: "250px"}}>
                        <div className="course-item">
                            <div className="row no-gutters">
                                <div className="col-1">
                                    <div className="course-index">
                            <span>
                                --
                            </span>
                                    </div>
                                </div>
                                <div className="col-3">
                                    <div className="course-cell">
                                        <span>وضعیت</span>
                                    </div>
                                </div>
                                <div className="col-2">
                                    <div className="course-cell">
                                        <span>کد</span>
                                    </div>
                                </div>
                                <div className="col-3">
                                    <div className="course-cell">
                            <span>
                                نام درس
                            </span>
                                    </div>
                                </div>
                                <div className="col-2">
                                    <div className="course-cell">
                            <span>
                                استاد
                            </span>
                                    </div>
                                </div>
                                <div className="col-1">
                                    <div className="course-cell" style={{borderLeft: "none"}}>
                            <span>
                                واحد
                            </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {this.renderSelectedList()}
                    </div>
                    <div className="selector-lower row no-gutters" style={{height: "60px"}}>
                        <div className="col-2">
                            <div className="units-box">
                     <span>
                         تعداد واحد ثبت شده: {this.state.lenOfSubmited}
                     </span>
                            </div>
                        </div>
                        <div className="col-1 mr-auto">
                            <div type="button" className="btn-reset" style={{height: "55px" , lineHeight: "55px"}}
                                 onClick={this.props.onReset}>
                                <i className="flaticon-refresh-arrow" style={{height: "100%"}}></i>
                            </div>
                        </div>
                        <div className="col-2">
                            <button type="button" className="btn-submit" style={{height: "55px" , lineHeight: "55px"}}
                                    onClick={this.props.onSubmit}>
                                ثبت نهایی
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    render() {
        return (
            <div>
                {this.props.selectedCourses !== null && this.state.lenOfSubmited !== null ?
                this.renderMain()
                     :
                this.renderLoading()
                 }
            </div>
        );
    }
}





import React from "react";
import CoursesService from "../../services/CoursesService";
import Presenteditem from "./Presenteditem";
import "../../Assets/styles/courses.css";
import PropTypes from "prop-types";

export default class CoursesPresented extends React.Component {

    constructor(props) {
        super(props);
        this.state = {

        }
    }

    renderPresentList() {
        return this.props.presentedCourses.map((courseItem) =>
            <div className="course-item">
                <div className="row no-gutters">
                    <Presenteditem courseItem={courseItem} key={courseItem.code} onAdd={this.props.onAdd} selectedCourses={this.props.selectedCourses} />
                </div>
            </div>
        );
    }

    render() {
        return (
            <div className="courses-charge-wrapper" style={{marginBottom: '100px'}}>
                <div className="courses-charge" style={{paddingTop: '180px'}}>
                    <div className="selector row" style={{right: '80px'}}>
                        <div className="selector-item active-item">
                            <span>
                                دروس ارائه شده
                            </span>
                        </div>
                    </div>
                    <div className="selector-upper row" style={{top: '0px', height: '120px'}}>
                        <div className="col-2">
                            <div type="button" className="btn-noe-course"
                            onClick={() => this.props.onFilter("")}>
                                <span>
                                     همه
                                 </span>
                            </div>
                        </div>
                        <div className="col-2">
                            <div type="button" className="btn-noe-course"
                             onClick={() => this.props.onFilter("Takhasosi")}>
                                 <span>
                                     اختصاصی
                                 </span>
                            </div>
                        </div>
                        <div className="col-2">
                            <div type="button" className="btn-noe-course"
                             onClick={() => this.props.onFilter("Asli")}>
                                 <span>
                                     اصلی
                                 </span>
                            </div>
                        </div>
                        <div className="col-2">
                            <div type="button" className="btn-noe-course"
                             onClick={() => this.props.onFilter("Paaye")}>
                                 <span>
                                     پایه
                                 </span>
                            </div>
                        </div>
                        <div className="col-2">
                            <div type="button" className="btn-noe-course"
                             onClick={() => this.props.onFilter("Umumi")}>
                                 <span>
                                     عمومی
                                 </span>
                            </div>
                        </div>
                    </div>
                    <div className="selector-upper row" style={{top: '120px', height: '60px'}}>
                        <div className="courses" style={{width: '100%', border: 'none'}}>
                            <div className="course-item" style={{borderBottom: 'none'}}>
                                <div className="row no-gutters">
                                    <div className="col-1">
                                        <div className="course-index" style={{border: 'none'}}>
                                            <span>
                                                --
                                            </span>
                                        </div>
                                    </div>
                                    <div className="col-2">
                                        <div className="course-cell" style={{border: 'none'}}>
                                            <span>
                                                کد
                                            </span>
                                        </div>
                                    </div>
                                    <div className="col-1">
                                        <div className="course-cell" style={{border: 'none'}}>
                                            <span>
                                                ظرفیت
                                            </span>
                                        </div>
                                    </div>
                                    <div className="col-1">
                                        <div className="course-cell" style={{border: 'none'}}>
                                            <span>
                                                نوع
                                            </span>
                                        </div>
                                    </div>
                                    <div className="col-2">
                                        <div className="course-cell" style={{border: 'none'}}>
                                            <span>
                                                نام درس
                                            </span>
                                        </div>
                                    </div>
                                    <div className="col-2">
                                        <div className="course-cell" style={{border: 'none'}}>
                                            <span>
                                                استاد
                                            </span>
                                        </div>
                                    </div>
                                    <div className="col-1">
                                        <div className="course-cell" style={{border: 'none'}}>
                                            <span>
                                                واحد
                                            </span>
                                        </div>
                                    </div>
                                    <div className="col-2">
                                        <div className="course-cell" style={{border: 'none'}}>
                                            <span>
                                                توضیحات
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="courses" style={{width: '100%', borderBottom: 'none'}}>
                        {this.renderPresentList()}
                    </div>
                </div>
            </div>
        );
    }
}







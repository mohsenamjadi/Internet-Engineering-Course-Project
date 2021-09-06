
export default class Translator {
    static toFa(enText) {
        switch (enText) {
            case "Umumi":
                return "عمومی";
            case "Asli":
                return "اصلی";
            case "Paaye":
                return "پایه";
            case "Takhasosi":
                return "تخصصی";
            case "finalized":
                return "ثبت شده";
            case "non-finalized":
                return "ثبت نهایی نشده";
            case "pending":
                return "در انتظار";
            case "status-box accepted":
                return "قبول";
            case "status-box not-accepted":
                return "مردود";
            case "status-box not-specified":
                return "نا مشخص";

            default:
                return enText
        }
    }
}
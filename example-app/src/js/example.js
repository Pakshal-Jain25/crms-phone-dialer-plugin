import { PhoneLogger } from '@capacitor/phone-logger';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    PhoneLogger.echo({ value: inputValue })
}

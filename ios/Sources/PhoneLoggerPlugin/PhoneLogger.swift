import Foundation

@objc public class PhoneLogger: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}

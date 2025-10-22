// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorPhoneLogger",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapacitorPhoneLogger",
            targets: ["PhoneLoggerPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "PhoneLoggerPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/PhoneLoggerPlugin"),
        .testTarget(
            name: "PhoneLoggerPluginTests",
            dependencies: ["PhoneLoggerPlugin"],
            path: "ios/Tests/PhoneLoggerPluginTests")
    ]
)
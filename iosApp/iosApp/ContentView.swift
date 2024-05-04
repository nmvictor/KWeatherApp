import SwiftUI
import Shared
import Combine


struct ContentView: View {
    @State private var showContent = false
    @State private var searchText = "Nairobi"
    @State private var report: WeatherReport? = nil
    @Environment(\.dismissSearch) private var dismissSearch
    var viewModel: WeatherReportViewModel!
   
    func handleSeerch() {
        dismissSearch()
        viewModel.weather.watch { result in
            guard let _report = result?.data else {
                return
            }
            self.report = _report
            withAnimation {
                showContent = true
            }
        }
        viewModel.loadWeatherReport(location: searchText)
    }
    
    init() {
        viewModel = WeatherReportViewModel();
    }
    var body: some View {
        NavigationStack {
            
            VStack {
                Button("Reload Weather!") {
                    handleSeerch()
                }

                if let report = report,
                    showContent {
                    HStack(spacing: 8) {
                        Image(systemName: "swift")
                            .font(.system(size: 64))
                            .foregroundColor(.accentColor)
                        VStack(spacing: 8) {
                            Text("\(report.current.condition.text) in \(report.location.name)")
                                .bold()
                            Text("Temp: \(Int(report.current.tempC)) °C \n Humidity: \( Int(report.current.humidity)) \n Wind: \(Int(report.current.windKPH)) kph")
                               
                        }
                       
                    }
                    .transition(.move(edge: .top).combined(with: .opacity))
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
            .padding()
            .navigationTitle(Text("Weather"))
        }
        .searchable(text: $searchText, prompt: "Enter City Name")
        .simultaneousGesture(DragGesture().onChanged({ _ in
            dismissSearch()
        }))
        .onSubmit(of: .search) {
            handleSeerch()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}


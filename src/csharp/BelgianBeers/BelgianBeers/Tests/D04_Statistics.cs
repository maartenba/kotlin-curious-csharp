using System.Linq;
using BelgianBeers.Tests.Utilities;
using Xunit;
using Xunit.Abstractions;

namespace BelgianBeers.Tests
{
    [Collection("Demo 4 - Statistics")]
    public class D04_Statistics
    {
        private readonly ITestOutputHelper _outputHelper;

        public D04_Statistics(ITestOutputHelper outputHelper)
        {
            _outputHelper = outputHelper;
        }

        [Fact]
        public void Statistics()
        {
            // Statistics:
            var topRatedBreweries = from beer in TestData.BeerFlow
                where beer.Brewery != null
                group beer by beer.Brewery into beersPerBrewery
                orderby beersPerBrewery.Average(beer => beer.Rating) descending
                select beersPerBrewery.Key;

            foreach (var brewery in topRatedBreweries.Take(10))
            {
                _outputHelper.WriteLine(brewery.Name);
            }
        }
    }
    
}
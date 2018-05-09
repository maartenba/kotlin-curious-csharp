using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BelgianBeers.Models;
using Xunit;
using Xunit.Abstractions;
using static BelgianBeers.Tests.Utilities.TestData;

namespace BelgianBeers.Tests
{
    [Collection("Demo 5 - Async")]
    public class D05_Async_JustTasks
    {
        private readonly Random _random = new Random();
        private readonly ITestOutputHelper _outputHelper;

        public D05_Async_JustTasks(ITestOutputHelper outputHelper)
        {
            _outputHelper = outputHelper;
        }

        [Fact]
        public Task FetchStrongestBeers()
        {
            // Create tasks
            var tasks = new List<Task<(Beer beer, double strongness)>>();
            
            foreach (var beer in BeerFlow)
            {
                var task = Task.Run(() => RetrieveBeerStrongness(beer))
                    .ContinueWith(strongnessResult =>
                    {
                        if (!strongnessResult.IsCompletedSuccessfully)
                        {
                            // TODO: handle Exception?
                        }

                        return (beer, strongnessResult.Result);
                    });
                
                tasks.Add(task);
            }
            
            return Task.WhenAll(tasks)
                .ContinueWith(result =>
                {
                    if (!result.IsCompletedSuccessfully)
                    {
                        // TODO: do something with result.Exception?
                    }
                    
                    // Get strongest beers
                    foreach (var (beer, strongness) in tasks
                        .Where(t => t.IsCompletedSuccessfully)
                        .Select(t => t.Result)
                        .OrderByDescending(r => r.strongness).Take(10))
                    {
                        _outputHelper.WriteLine($"{strongness:P} - {beer.Name}");
                    }
                });
        }

        private Task<double> RetrieveBeerStrongness(Beer beer)
        {
            return Task.Delay(_random.Next(10, 300))
                .ContinueWith(result =>
                {
                    if (!result.IsCompletedSuccessfully)
                    {
                        // TODO: handle Exception?
                    }

                    return _random.NextDouble() * _random.NextDouble() * 0.2 / 2;
                });
        }
    }
}
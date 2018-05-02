using System;
using JetBrains.Annotations;

namespace BelgianBeers.Models
{
    public class Beer
    {
        public Beer(string name, Brewery brewery, double rating, double votes)
        {
            Name = name;
            Brewery = brewery;
            Rating = rating;
            Votes = votes;
        }

        public string Name { get; }
        public Brewery Brewery { get; }
        public double Rating { get; }
        public double Votes { get; }

        public void Deconstruct(out string name, out Brewery brewery, out double rating, out double votes)
        {
            name = Name;
            brewery = Brewery;
            rating = Rating;
            votes = Votes;
        }

        public void Deconstruct(out string name, out string brewery, out double rating, out double votes)
        {
            name = Name;
            brewery = Brewery?.Name;
            rating = Rating;
            votes = Votes;
        }

        protected bool Equals(Beer other)
        {
            return string.Equals(Name, other.Name, StringComparison.OrdinalIgnoreCase) && Equals(Brewery, other.Brewery);
        }

        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            if (obj.GetType() != this.GetType()) return false;
            return Equals((Beer) obj);
        }

        public override int GetHashCode()
        {
            unchecked
            {
                return ((Name != null ? StringComparer.OrdinalIgnoreCase.GetHashCode(Name) : 0) * 397) ^ (Brewery != null ? Brewery.GetHashCode() : 0);
            }
        }
    }
    
    #region Beer specialties
    
    public class TripelBeer : Beer
    {
        public TripelBeer(string name, Brewery brewery, double rating, double votes) 
            : base(name, brewery, rating, votes)
        {
        }
    }

    public class DubbelBeer : Beer
    {
        public DubbelBeer(string name, Brewery brewery, double rating, double votes) 
            : base(name, brewery, rating, votes)
        {
        }
    }
    
    #endregion
}